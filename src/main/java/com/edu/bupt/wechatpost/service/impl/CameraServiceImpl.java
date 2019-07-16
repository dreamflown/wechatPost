package com.edu.bupt.wechatpost.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.dao.CameraMapper;
import com.edu.bupt.wechatpost.dao.CameraUserMapper;
import com.edu.bupt.wechatpost.dao.CameraUserRelationMapper;
import com.edu.bupt.wechatpost.model.Camera;
import com.edu.bupt.wechatpost.model.CameraUser;
import com.edu.bupt.wechatpost.model.CameraUserRelation;
import com.edu.bupt.wechatpost.service.CameraService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CameraServiceImpl implements CameraService {

    private JSONObject appInfo = new JSONObject();
    private static OkHttpClient client = new OkHttpClient();

    @Autowired
    private CameraMapper cameraMapper;

    @Autowired
    private CameraUserMapper userMapper;

    @Autowired
    private CameraUserRelationMapper relationMapper;


    private JSONObject  getAppInfoByuserInfo(Integer customerId){
//        this.appInfo.clear();
//        this.appInfo.put("appKey","2202b037f424462888e3918831dd9680");
//        this.appInfo.put("appSecret","4e45ac4dbaf66fddb8afb4da7e313cef");
        CameraUser user = userMapper.selectByPrimaryKey(customerId);
        return JSONObject.parseObject(user.toString());
    }

    public String register(String appKey, String appSecret){
        String resp = "succeed";
        CameraUser user = new CameraUser();
        user.setAppkey(appKey);
        user.setAppsecret(appSecret);
        int insert = userMapper.insertSelective(user);
        if (insert == 0){
            resp = "fail";
        }
        return resp;
    }

    public String updateUserInfo(CameraUser user){
        int update = userMapper.updateByPrimaryKeySelective(user);
        if (update == 0){
            return "false";
        }
        return "succeed";
    }

    private String POST(Request request){
        String ret = null;
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                JSONObject resultJson = JSONObject.parseObject(result);
                if (true == resultJson.getString("code").equals("200")) {
                    ret = result;
                }else{
                    System.out.println("post code error:"+resultJson.getString("code"));
                    ret = null;
                }
            }else{
                System.out.println("respose failed");
                ret = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            ret = null;
        }
        return  ret;
    }


    public CameraUser getAccessToken(Integer customerId){
        CameraUser user = null;
        user = userMapper.selectByPrimaryKey(customerId);
        return user;
    }

    public boolean validAccessToken(CameraUser user) {
        String timestamp = user.getStore();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date last = format.parse(timestamp,pos);
        Date now = new Date();
        long yet = now.getTime() - last.getTime()/ 1000L; // 距离上一次更新过了多少秒
        return yet < 1563856469974L;
    }

    public String sendForaccessToken(Integer customerId) {
        String postUrl = "https://open.ys7.com/api/lapp/token/get";
        String result = new String();
        CameraUser user = userMapper.selectByPrimaryKey(customerId);
        System.out.println(user.getAppkey());
        System.out.println(user.getAppsecret());
        if(null == user){
            result = "404";
        }else {
            okhttp3.RequestBody body = new FormBody.Builder()
                    .add("appKey", user.getAppkey())
                    .add("appSecret", user.getAppsecret()).build();
            Request request = new Request.Builder()
                    .url(postUrl)
                    .post(body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    result = response.body().string();
                    JSONObject resultJson = JSONObject.parseObject(result);
                    if (true == resultJson.getString("code").equals("200")) {
                        result = resultJson.getJSONObject("data").getString("accessToken");
                        user.setAccesstoken(result);
                    } else {
                        result = "500";
                    }
                } else {
                    result = "500";
                }
            } catch (IOException e) {
                e.printStackTrace();
                result = "500";
            }
        }
        return result;
    }

    public JSONObject getLiveAddressList(Integer customerId){

        String postUrl = "https://open.ys7.com/api/lapp/live/video/list";
        JSONObject ret = new JSONObject();
        CameraUser user = getaccessToken(customerId);
        String accessToken = new String();
        if (user == null) {
            return ret;
        }
        if (!validAccessToken(user)) {
            accessToken = sendForaccessToken(customerId);
        } else {
            accessToken = user.getAccesstoken();
        }
        okhttp3.RequestBody body = new FormBody.Builder()
                .add("accessToken", accessToken).build();
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        String result  = this.POST(request);
        System.out.println(result);
        if(null != result){
            JSONObject resultJson = JSONObject.parseObject(result);
            ret.put("data",resultJson.getJSONArray("data"));
            ret.put("code","200");
        }else{
            ret.put("code","500");
            ret.put("data","内部错误");
        }
        return ret;
    }

    public JSONObject getLiveAddrBydeviceSerial(Integer customerId, String deviceSerial,String Cam){

        String postUrl = "https://open.ys7.com/api/lapp/live/address/get";
        JSONObject ret = new JSONObject();

                CameraUser user = getaccessToken(customerId);
        String accessToken = new String();
        if (user == null) {
            return ret;
        }
        if (!validAccessToken(user)) {
            accessToken = sendForaccessToken(customerId);
        } else {
            accessToken = user.getAccesstoken();
        }

        okhttp3.RequestBody body = new FormBody.Builder()
                .add("accessToken", accessToken)
                .add("source",deviceSerial+":"+Cam).build();
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        String response = this.POST(request);
        if(null != response){
            System.out.println(response);
            ret.put("data",JSONObject.parseObject(response).getJSONArray("data"));
        }else{
            System.out.println("post error");
            ret = null;
        }
        return ret;
    }

    public JSONArray openLiveBydeviceSerial(Integer customerId,String deviceSerial,String Cam){
        String postUrl = "https://open.ys7.com/api/lapp/live/video/open";
        JSONObject appInfo = getAppInfoByuserInfo(customerId);
        JSONArray ret = new JSONArray();
                CameraUser user = getaccessToken(customerId);
        String accessToken = new String();
        if (user == null) {
            return ret;
        }
        if (!validAccessToken(user)) {
            accessToken = sendForaccessToken(customerId);
        } else {
            accessToken = user.getAccesstoken();
        }
        okhttp3.RequestBody body = new FormBody.Builder()
                .add("accessToken", accessToken)
                .add("source",deviceSerial+":"+Cam).build();
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        String response = this.POST(request);
        if(null != response){
            ret = JSONObject.parseObject(response).getJSONArray("data");
        }else{
            System.out.println("post error");
            ret = null;
        }
        return ret;
    }

    public JSONArray closeLiveBydeviceSerial(Integer customerId,String deviceSerial,String Cam){
        String postUrl = "https://open.ys7.com/api/lapp/live/video/close";
        JSONObject appInfo = getAppInfoByuserInfo(customerId);
        JSONArray ret = new JSONArray();
                CameraUser user = getaccessToken(customerId);
        String accessToken = new String();
        if (user == null) {
            return ret;
        }
        if (!validAccessToken(user)) {
            accessToken = sendForaccessToken(customerId);
        } else {
            accessToken = user.getAccesstoken();
        }
        okhttp3.RequestBody body = new FormBody.Builder()
                .add("accessToken", accessToken)
                .add("source",deviceSerial+":"+Cam).build();
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        String response = this.POST(request);
        if(null != response){
            ret = JSONObject.parseObject(response).getJSONArray("data");
        }else{
            System.out.println("post error");
            ret = null;
        }
        return ret;
    }

    public JSONObject getDevicecapacity(Integer customerId, String deviceSerial){
        String postUrl = "https://open.ys7.com/api/lapp/device/capacity";
        JSONObject appInfo = getAppInfoByuserInfo(customerId);
        JSONObject ret = new JSONObject();
                CameraUser user = getaccessToken(customerId);
        String accessToken = new String();
        if (user == null) {
            return ret;
        }
        if (!validAccessToken(user)) {
            accessToken = sendForaccessToken(customerId);
        } else {
            accessToken = user.getAccesstoken();
        }
        okhttp3.RequestBody body = new FormBody.Builder()
                .add("accessToken", accessToken)
                .add("deviceSerial",deviceSerial).build();
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        String response = this.POST(request);
        if(null != response){
            ret = JSONObject.parseObject(response).getJSONObject("data");
        }else{
            System.out.println("post error");
            ret = null;
        }
        return ret;
    }

    public JSONObject addDevice(Integer customerId, String serial, String validateCode, Camera camera){
        String postUrl = "https://open.ys7.com/api/lapp/device/add";
        JSONObject appInfo = getAppInfoByuserInfo(customerId);
        JSONObject ret = new JSONObject();
                CameraUser user = getaccessToken(customerId);
        String accessToken = new String();
        if (user == null) {
            return ret;
        }
        if (!validAccessToken(user)) {
            accessToken = sendForaccessToken(customerId);
        } else {
            accessToken = user.getAccesstoken();
        }
        okhttp3.RequestBody body = new FormBody.Builder()
                .add("accessToken", accessToken)
                .add("deviceSerial",serial)
                .add("validateCode",validateCode).build();
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        String response = this.POST(request);
        if(null != response){
            // add device into database
            ret = JSONObject.parseObject(response);
            String id = UUID.randomUUID().toString();
            camera.setId(id);
            if (camera.getName().equals("")){ // 取id最后四位作为默认名
                camera.setName("camera_"+ id.substring(id.length()-4, id.length()));
            }
            for (int i =0; i<3; i++) {
                int add = dealAddDevice(customerId, camera);
                if (add != 0) {
                    ret.put("sql", "success");
                    break;
                } else {
                    ret.put("sql", "fail");
                    continue;
                }
            }
        }else{
            System.out.println("post error");
            ret = null;
        }
        return ret;
    }

    public JSONObject delDevice(Integer customerId,String deviceSerial, String camera_id){
        String postUrl = "https://open.ys7.com/api/lapp/device/delete";
//        JSONObject appInfo = getAppInfoByuserInfo(customerId);
        JSONObject ret = new JSONObject();
        CameraUser user = getaccessToken(customerId);
        String accessToken = new String();
        if (user == null) {
            return ret;
        }
        if (!validAccessToken(user)) {
            accessToken = sendForaccessToken(customerId);
        } else {
            accessToken = user.getAccesstoken();
        }
        okhttp3.RequestBody body = new FormBody.Builder()
                .add("accessToken", accessToken)
                .add("deviceSerial",deviceSerial).build();
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        String response = this.POST(request);
        if(null != response){
            ret = JSONObject.parseObject(response);
            for (int i = 0; i<3; i++) {
                int delete = dealDeleteDevice(customerId, camera_id);
                if (delete != 0) {
                    ret.put("sql", "success");
                    break;
                } else {
                    ret.put("sql", "fail");
                    continue;
                }
            }
        }else{
            System.out.println("post error");
            ret = null;
        }
        return ret;
    }

    @Override
    public List<Camera> dealGetDevices(Integer customerId, String method)throws Exception{

        List<Camera> cameras = new ArrayList<Camera>();
        try {
            List<String> cameraList = relationMapper.selectCameraIdByCustomerId(customerId);
            if (cameraList.size() != 0) {
                for (String id: cameraList){
                    try {
                        Camera camera = cameraMapper.selectByPrimaryKey(id);
                        cameras.add(camera);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if (cameras.size() == 0){
            return null;
        }

        // sort
        List<Camera> newCameras = new ArrayList<>();
        switch (method) {
            case "time": // 按加入数据库的时间先后排序
                cameras.stream().sorted(Comparator.comparing((Camera ca) -> ca.getLastOnline())).collect(Collectors.toList());
                break;
            case "name": // 按名称字典序排序
                cameras.stream().sorted(Comparator.comparing((Camera ca) -> ca.getName())).collect(Collectors.toList());
                break;
            case "manufacturer": // 按厂商排序
                cameras.stream().sorted(Comparator.comparing((Camera ca) -> ca.getManufature())).collect(Collectors.toList());
                break;
            case "model": // 按型号排序
                cameras.stream().sorted(Comparator.comparing((Camera ca) -> ca.getModel())).collect(Collectors.toList());
                break;
            case "group": // 按组排序
                cameras.stream().sorted(Comparator.comparing((Camera ca) -> ca.getGroup())).collect(Collectors.toList());
                break;
            default:
                cameras.stream().sorted(Comparator.comparing((Camera ca) -> ca.getName())).collect(Collectors.toList());
                break;
        }
        return cameras;
    }



    @Override
    public JSONObject  updateDeviceInfo(Camera camera) {
        JSONObject ret  = new JSONObject();
        int update = cameraMapper.updateByPrimaryKey(camera);
        if (update != 0){
            ret.put("sql","success");
        } else {
            ret.put("sql", "fail");
        }
        return ret;
    }

    @Override
    public int updateAlarmSettings(Integer customerId, String serial) {
        return 0;
    }

    @Override
    public int dealAddDevice(Integer customerId, Camera camera) {
        int insert = cameraMapper.insertSelective(camera);
        if(insert != 0) {
            CameraUserRelation r = new CameraUserRelation(camera.getId(), customerId);
            return relationMapper.insertSelective(r);
        }
        return 0;
    }

    @Override
    public int dealDeleteDevice(Integer customerId, String camera_id) {
        // 删除 camera_account_relation 中的关联关系

        int delete = relationMapper.deleteByCustomerIdAndCameraId(customerId, camera_id);
        if (delete != 0) {
            // 删除 camera 中的记录
            return cameraMapper.deleteByPrimaryKey(camera_id);
        }
        return 0;
    }

}
