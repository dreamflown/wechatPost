package com.edu.bupt.wechatpost.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.dao.AuthMapper;
import com.edu.bupt.wechatpost.dao.LikeRelationMapper;
import com.edu.bupt.wechatpost.dao.MomentTipMapper;
import com.edu.bupt.wechatpost.dao.PostCommentMapper;
import com.edu.bupt.wechatpost.model.*;
import com.edu.bupt.wechatpost.service.WxService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

@Service
public class WxServiceImpl implements WxService{
    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private MomentTipMapper tipMapper;

    @Autowired
    private LikeRelationMapper likeMapper;

    @Autowired
    private PostCommentMapper postCommentMapper;

    private okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();

    private static ResultCallBack defaultResultCallback = new ResultCallBack() {
        @Override
        public void onCallBack(String result) {
            System.out.println("由于未定义ResultCallback参数,以下信息未被处理" +
                    "\tresult:" + result);
        }
    };


    /**
     * 向微信服务器申请 openid，并根据unionid建立小程序和公众号对应表
     * @param message （小程序前端的 JSCODE）
     * @return
     */
    public String getOpenId(JSONObject message) {
        final String JSCODE = message.getString("JSCODE");

        // 小程序管理后台开发者配置参数
        final String appid = "wx9e12afc5dec75b6f";
        final String secret = "d0d7b3d2ab48530710a4828003dd1c05";

        String unionid = null;
        String returnvalue = "";

        // 获取 openid (和 unionid)
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type='authorization_code'"
                .replace("APPID",appid)
                .replace("SECRET",secret)
                .replace("JSCODE",JSCODE);
        try {
            returnvalue = GET(url);
        } catch (IOException e) {
            System.out.println("ERROR : 请求失败");
            e.printStackTrace();
            return "error!";
        }

        // 更新小程序和公众号 openid 对照表
        if (!returnvalue.equals("")) {
            System.out.println(String.format("请求成功，返回值为 : %s", returnvalue)); // 打印调用GET方法返回值

            // 解析返回结果
            JSONObject convertvalue = (JSONObject) JSONObject.parse(returnvalue);
            String openid = convertvalue.getString("openid");
            try {
                unionid = convertvalue.getString("unionid");
            } catch (NullPointerException e) {
                System.out.println(" NOTE : unionid 为空，用户未关注公众号,将无法收到报警消息");
            }

            // 若微信服务器返回 unionid 不为空， 向 auth 表插入 unionid 和小程序 openid
            if (null != unionid) {
                // 查找数据库是否已存在该 unionid 的对应关系
                Auth user = authMapper.selectByUnionid(unionid);
                if (null == user) {  // 数据库不存在该记录
                    // 插入 unionid 和小程序 openid
                    authMapper.insertSelective(new Auth(unionid, openid, ""));

                    // RPC 调用 wechatplugin 插件的函数，更新公众号 openid 列表 (Auth)
                    okhttp3.Request request = new okhttp3.Request.Builder().url("http://47.105.120.203:30080/api/v1/wechatplugin/getAllUsers").get().build();
                    WxService.ResultCallBack callback = new ResultCallBack() {
                        @Override
                        public void onCallBack(String result) {
                            System.out.println("公众号关注用户列表更新成功");
                        }
                    };
                    ExecuteAsyn(request, callback);
//                    try {
//                        okhttp3.Response res = client.newCall(request).execute();
//                    } catch (IOException e) {
//                        System.out.println("公众号更新关注用户失败");
//                        e.printStackTrace();
//                    }

                } else {  // 数据库存在该记录
                    // 小程序 openid 为空则插入
                    String miniopenid_temp = user.getMini_openid();
                    if (null == miniopenid_temp || "".equals(miniopenid_temp)) {
                        authMapper.updateMiniOpenid(unionid, openid);
                    }

                    // 公众号 openid 为空则更新关注用户列表
                    String oaopenid_temp = user.getOa_openid();
                    if (null == oaopenid_temp || "".equals(oaopenid_temp)) {
                        // RPC 调用更新公众号 openid
                        okhttp3.Request request = new okhttp3.Request.Builder().url("http://47.105.120.203:30080/api/v1/wechatplugin/getAllUsers").get().build();
                        WxService.ResultCallBack callback = new ResultCallBack() {
                            @Override
                            public void onCallBack(String result) {
                                System.out.println("公众号关注用户列表更新成功");
                            }
                        };
                        ExecuteAsyn(request, callback);
//                        try {
//                            okhttp3.Response res = client.newCall(request).execute();
//                        } catch (IOException e) {
//                            System.out.println("公众号更新关注用户失败");
//                            e.printStackTrace();
//                        }
                    }
                }
            }

            return convertvalue.toJSONString();

        }else {
            System.out.println("ERROR : 微信服务器返回数据为空!");
            return "error!";
        }

    }

    /**
     * 判断用户是否关注公众号
     * @param message
     * @return
     */
    public int follow(JSONObject message) {
        // 解析 json 数据
        String unionid = message.getString("unionid");
        String openid = message.getString("openid");

        System.out.print("用户是否关注公众号 ==> ");
        if(null != unionid && !"".equals(unionid)) {
            Auth user = authMapper.selectByUnionid(unionid);
            if (null != user) {
                // 公众号和小程序 openid 存在对应关系
                String mini_openid = user.getMini_openid();
                String oa_openid = user.getOa_openid();
                if ( null != mini_openid && !("".equals(mini_openid)) && null != oa_openid && !("".equals(oa_openid)) ){
                    System.out.println("已关注");
                    return 1;
                }
            }
        }
        System.out.println("未关注");
        return 0;

//        if(null != unionid && !"".equals(unionid)){ // unionid 不为空
//            Auth user = authMapper.selectByUnionid(unionid);
//            if (user != null) {  // 用户存在
//                String mini_openid = user.getMini_openid();
//                if( null == mini_openid || "".equals(mini_openid)){
//                    authMapper.updateMiniOpenid(unionid, openid);
//                }
//                String oa_openid = user.getOa_openid();
//                if ( null != oa_openid && !"".equals(oa_openid)){  // 用户已关注微信公众号
//                    return 1; // followed
//                } else {  // 更新数据库确认是否关注公众号
//                    okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
//                    okhttp3.Request request = new okhttp3.Request.Builder().url("http://47.105.120.203:30080/api/v1/wechatplugin/getAllUsers").get().build();
//                    try {
//                        okhttp3.Response res = client.newCall(request).execute();
//                        if (res.isSuccessful()){
//                            if(res.body().string() != "-1"){  // 更新失败则返回 -1
//                                if(null != authMapper.selectOAByUnionid(unionid))
//                                    return 1;
//                            }
//                        }
//                    } catch (IOException e){
//                        e.printStackTrace();
//                    }
//                    return 0; // unregiste
//                }
//            } else {
//                user.setUnionid(unionid);
//                user.setMini_openid(openid);
//                authMapper.insertSelective(user); // 未发现该用户，插入新纪录
//                return 0;  // empty
//            }
//        }

    }

    public String GET(String url) throws IOException {
        String result = "";
        BufferedReader in = null;
        InputStream is = null;
        InputStreamReader isr = null;
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();
            is = conn.getInputStream();
            isr = new InputStreamReader(is);
            in = new BufferedReader(isr);
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace(); // 异常记录
            }
        }
        return result;
    }

    public void registe(String unionid, String oa_openid) {
        Auth user = new Auth(unionid,null, oa_openid);
        try {
            authMapper.insertSelective(user);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addUser(String unionid, String oa_openid){
        // 如果数据库存在该用户则不插入
        if(authMapper.selectByUnionid(unionid)!=null) return;
        Auth user = new Auth(unionid, null, oa_openid);
        authMapper.insertSelective(user);
    }

    public void get_and_insert_users(String access_token){
        String GET_USERLIST_URL = String.format("https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s&next_openid=",access_token);
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(GET_USERLIST_URL).get().build();
        try {
            okhttp3.Response res = client.newCall(request).execute();
            if(res.isSuccessful()){
                // 解析返回结果
                String result = res.body().string();
                JSONObject json = JSONObject.parseObject(result);
                int total = json.getInteger("total");
                int count = json.getInteger("count");
                JSONObject data = json.getJSONObject("data");
                JSONArray openids = data.getJSONArray("openid");
                String next_openid = json.getString("next_openid");
                // 如果(total - count)为0, 用户列表全部获取到
                while(total >= 0) {
                    total -= count;
                    for (int i = 0; i < count; i++) {
                        // 对每一个openid，通过查找用户信息API获得对应的unionid
                        String openid = openids.getString(i);
                        String GET_USERINFO_URL = String.format("https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s", access_token, openid);
                        request = new okhttp3.Request.Builder().url(GET_USERINFO_URL).get().build();
                        try {
                            res = client.newCall(request).execute();
                            if (res.isSuccessful()) {
                                String next_result = res.body().string();
                                json = JSONObject.parseObject(next_result);
                                String unionid = json.getString("unionid");
                                // 插入新纪录
                                addUser(unionid, openid);
                            }
                        } catch (IOException e) {
                            System.out.println(String.format("========== ERROR:获取 %s 用户信息失败", openid));
                            e.printStackTrace();

                        }
                    }
                    if(total > 0){  // 用户列表不全,获取剩余用户
                        String next_url = GET_USERLIST_URL + next_openid;
                        request = new okhttp3.Request.Builder().url(next_url).get().build();
                        try {
                            res = client.newCall(request).execute();
                            if (res.isSuccessful()){
                                json = JSONObject.parseObject(res.body().string());
                                count = json.getInteger("count");
                                next_openid = json.getString("next_openid");
                                data = json.getJSONObject("data");
                                openids = data.getJSONArray("openid");
                            }
                        } catch(IOException e){
                            System.out.println("========== ERROR:后续获取用户列表失败");
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            }
        } catch (IOException e){
            System.out.println("========== ERROR:第一次获取用户列表失败");
            e.printStackTrace();
        }
    }

    public Boolean isLike(String openid, Integer p_id) {
        return (null != likeMapper.selectByOpenidAndPid(openid, p_id));
    }

    public Integer addLikeRelation(LikeRelation relation){
        likeMapper.insert(relation);
        Integer id = relation.getId();
        return id;
    }

    public Integer favorite(Integer pId, Integer num) {
        return postCommentMapper.updateFavoriteNum(pId, num);
    }

    public void unfavorite(String openid, Integer p_id) {
        likeMapper.delete(openid, p_id);
        postCommentMapper.updateFavoriteNum(p_id, -1);
    }

    public JSONArray getTips(String openid) {
        List<MomentTip> tips = tipMapper.selectByOpenid(openid);
        if(null == tips) return null;
        JSONArray jsonArray = new JSONArray();
        java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (MomentTip tip : tips){
            JSONObject json = new JSONObject();
            json.put("id", tip.getId());
            json.put("createTime", format.format(tip.getCreateTime()));
            json.put("avator", tip.getAvator());
            json.put("isread", tip.getIsread());
            Integer id = tip.getAction_id();
            switch (tip.getAction_type()){
                case 1: // LIKE
                     LikeRelation relation = likeMapper.selectById(id);
                     if (null != relation){
                         json.put("nickname", relation.getNickname());
                         json.put("p_id", relation.getP_id());
                         Post post = postCommentMapper.selectPostById(relation.getP_id());
                         if (null != post) {
                             json.put("p_content", post.getpContent());
                             json.put("image", post.getImage());
                         }
                     }
                    break;
                case 2:
                    Comment comment = postCommentMapper.selectCommentById(id);
                    if(null != comment) {
                        json.put("c_content", comment.getcContent());
                        json.put("nickname", comment.getNickName());
                        json.put("p_id", comment.getpId());
                        Post post = postCommentMapper.selectPostById(comment.getpId());
                        if (null != post) {
                            json.put("p_content", post.getpContent());
                            json.put("image", post.getImage());
                        }
                    }
                    break;
                default:
                    System.out.println("unknown record!");
                    break;
            }
            jsonArray.add(json);
        }
        return jsonArray;
    }

    public JSONArray getUnreadTips(String openid){
        List<MomentTip> tips = tipMapper.selectUnreadByOpenid(openid);
        if(null == tips) return null;
        JSONArray jsonArray = new JSONArray();
        java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (MomentTip tip : tips){
            JSONObject json = new JSONObject();
            json.put("id", tip.getId());
            json.put("createTime", format.format(tip.getCreateTime()));
            json.put("avator", tip.getAvator());
            Integer id = tip.getAction_id();
            switch (tip.getAction_type()){
                case 1: // Like
                    LikeRelation relation = likeMapper.selectById(id);
                    if (null != relation){
                        json.put("nickname", relation.getNickname());
                        json.put("p_id", relation.getP_id());
                        Post post = postCommentMapper.selectPostById(relation.getP_id());
                        if (null != post) {
                            json.put("p_content", post.getpContent());
                            json.put("image", post.getImage());
                        }
                    }
                    break;
                case 2:  // Comment
                    Comment comment = postCommentMapper.selectCommentById(id);
                    if(null != comment) {
                        json.put("c_content", comment.getcContent());
                        json.put("nickname", comment.getNickName());
                        json.put("p_id", comment.getpId());
                        Post post = postCommentMapper.selectPostById(comment.getpId());
                        if (null != post) {
                            json.put("p_content", post.getpContent());
                            json.put("image", post.getImage());
                        }
                    }
                    break;
                default:
                    System.out.println("unknown record!");
                    break;
            }
            jsonArray.add(json);
        }
        return jsonArray;
    }

    public void readTips(String openid) {
        tipMapper.updateIsread(openid, 1);
    }

    public void ExecuteAsyn(Request request, WxService.ResultCallBack callback) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onCallBack("公众号列表更新失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    callback.onCallBack("公众号列表更新成功");
                } else {
                    callback.onCallBack(Integer.toString(response.code()));
                }
            }
        });

    }

}