package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/smartruler")
public class SmartrulerController {
    private final String BASEURL = "http://47.105.120.203:30080/api/v1/smartruler/";
    private static OkHttpClient client = new OkHttpClient();

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String createRule(@RequestBody JSONObject message){
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "add")
                .post(body)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/remove/{ruleId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteRule(@PathVariable("ruleId")Integer ruleId){
        Request request = new Request.Builder()
                .url(BASEURL + "remove/" + ruleId)
                .delete()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/rule/{ruleId}", method = RequestMethod.GET)
    @ResponseBody
    public String getRuleById(@PathVariable("ruleId")Integer ruleId){
        Request request = new Request.Builder()
                .url(BASEURL + "rule/" + ruleId)
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/{ruleId}/activate", method = RequestMethod.POST)
    @ResponseBody
    public String activateRuleById(@PathVariable("ruleId")Integer ruleId){
        okhttp3.MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .url(BASEURL + ruleId + "/activate")
                .post(body)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/{ruleId}/suspend", method = RequestMethod.POST)
    @ResponseBody
    public String suspendRuleById(@PathVariable("ruleId")Integer ruleId){
        okhttp3.MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .url(BASEURL + ruleId + "/suspend")
                .post(body)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/rules", method = RequestMethod.GET)
    @ResponseBody
    public String getRules(){
        Request request = new Request.Builder()
                .url(BASEURL + "rules")
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
                return result;
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/plugin/all", method = RequestMethod.GET)
    @ResponseBody
    public String getPlugins(){
        Request request = new Request.Builder()
                .url(BASEURL + "plugin/all")
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/ruleByGateway/{gatewayId}", method = RequestMethod.GET)
    @ResponseBody
    public String getRuleByGatewayId(@PathVariable("gatewayId")String gatewayId){
        Request request = new Request.Builder()
                .url(BASEURL + "ruleByGateway/" + gatewayId)
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/ruleByGateway/{gatewayId}/{textSearch}", method = RequestMethod.GET)
    @ResponseBody
    public String getRulesByGatewayIdAndText(@PathVariable("gatewayId")String gatewayId,
                                            @PathVariable("textSearch")String textSearch){
        Request request = new Request.Builder()
                .url(BASEURL + "ruleByGateway/"+ gatewayId + "/" + textSearch)
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/alarmRule/suspend/{gatewayId}", method = RequestMethod.GET)
    @ResponseBody
    public String suspendAlarmRule(@PathVariable("gatewayId")String gatewayId){
        Request request = new Request.Builder()
                .url(BASEURL + "alarmRule/suspend/" + gatewayId )
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/alarmRule/activate/{gatewayId}", method = RequestMethod.GET)
    @ResponseBody
    public String activateAlarmRule(@PathVariable("gatewayId")String gatewayId){
        Request request = new Request.Builder()
                .url(BASEURL + "alarmRule/activate/" + gatewayId )
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/alarmActiveRule/{gatewayId}", method = RequestMethod.GET)
    @ResponseBody
    public String getAlarmActiveRule(@PathVariable("gatewayId")String gatewayId){
        Request request = new Request.Builder()
                .url(BASEURL + "alarmActiveRule/" + gatewayId )
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

}
