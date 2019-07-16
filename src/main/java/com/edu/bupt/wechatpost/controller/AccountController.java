package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final String BASEURL = "http://47.105.120.203:30080/api/v1/account/";

    private static OkHttpClient client = new OkHttpClient();

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestBody JSONObject message){
        System.out.println("===============  account: create user   +++++++++++++++++");
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "createUser")
                .post(body)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(@RequestBody JSONObject message){
        System.out.println("===============  account: login   +++++++++++++++++");
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "userLogin")
                .post(body)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/unBinderGates", method = RequestMethod.POST)
    @ResponseBody
    public String unBinderGates(@RequestBody JSONObject message){
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "unBinderGates")
                .post(body)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/unBindedGate", method = RequestMethod.POST)
    @ResponseBody
    public String unBindedGate(@RequestBody JSONObject message){
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "unBindedGate")
                .post(body)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/unBindedALLGate", method = RequestMethod.POST)
    @ResponseBody
    public String unBindedALLGate(@RequestBody JSONObject message){
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "unBindedALLGate")
                .post(body)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getBinderGates", method = RequestMethod.POST)
    @ResponseBody
    public String getBinderGates(@RequestBody JSONObject message){
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "getBinderGates")
                .post(body)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/getGates", method = RequestMethod.POST)
    @ResponseBody
    public String getGateways(@RequestBody JSONObject message){
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "getGates")
                .post(body)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/bindGate", method = RequestMethod.POST)
    @ResponseBody
    public String bindGateway(@RequestBody JSONObject message){
        final MediaType JSON = MediaType.parse("application/json,text/plain; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "bindGate")
                .post(body)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/unBindGate", method = RequestMethod.POST)
    @ResponseBody
    public String unBindGate(@RequestBody JSONObject message) throws Exception{
        final MediaType JSON = MediaType.parse("application/json,text/plain; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "unBindGate")
                .post(body)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}
