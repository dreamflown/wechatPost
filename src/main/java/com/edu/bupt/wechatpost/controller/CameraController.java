package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.service.CameraService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.edu.bupt.wechatpost.model.Camera;

import java.sql.Timestamp;
import java.util.List;


@RestController
@RequestMapping("/api/v1/camera")
public class CameraController {

    @Autowired
    private CameraService cameraService;


//    @ApiOperation(value = "getAccessTocken",notes = "get the tocken forme ys7")
//    @ApiImplicitParam(name = "appInfo", value = "the username and passwd info", required = true, dataType = "JSONObject")
    @RequestMapping(value = "/getTocken", method = RequestMethod.GET)
    public String getTocken(@RequestParam("customerId")Integer id) throws Exception{
        return cameraService.getAccessTocken(id);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String getTocken(@RequestBody JSONObject appInfo) throws Exception{
        String appKey = appInfo.getString("appKey");
        String appSecret = appInfo.getString("appSecret");
        return cameraService.register(appKey, appSecret);
    }

    /**
     * 获取摄像头流地址列表
     * @param serial
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getLiveAddress", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray getLiveAddressList(@RequestParam("customerId")Integer id,
                                        @RequestParam(value = "serial", required = false)String serial) throws Exception{

//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);
        if(!serial.equals("")) {
            return cameraService.getLiveAddrBydeviceSerial(id,serial,"1");
        }
        return cameraService.getLiveAddressList(id);
    }

//    @RequestMapping(value = "/getLiveAddress/serial", method = RequestMethod.GET)
//    @ResponseBody
//    public JSONArray getLiveAddrByDeviceSerial(@RequestParam(value = "user",required =  true) String user,
//                                                     @RequestParam(value="passwd",required = true) String passwd,
//                                                     @RequestParam(value = "serial",required = true)String serial) throws Exception{
//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);
//        return cameraService.getLiveAddrByserial(userInfo,serial,"1");
//    }

    /**
     * 打开视频流
     * @param serial
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/openLive", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray openLiveByserial(@RequestParam("customerId")Integer id,
                                      @RequestParam(value = "serial", required = false)String serial) throws Exception{
//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);
        return cameraService.openLiveBydeviceSerial(id,serial,"1");

    }

    /**
     * 关闭视频流
     * @param serial
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/closeLive", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray closeLiveByserial(@RequestParam("customerId")Integer id,
                                       @RequestParam(value = "serial", required = false)String serial) throws Exception{
//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);
        return cameraService.closeLiveBydeviceSerial(id,serial,"1");

    }

    @RequestMapping(value = "/getCapacity", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getDevicecapacitybySerial(@RequestParam("customerId")Integer id,
                                                @RequestParam(value = "serial", required = false)String serial) throws Exception{
//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);
        return cameraService.getDevicecapacity(id,serial);
    }

    /**
     * 获取摄像头列表并排序
     * @param method
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDevices", method = RequestMethod.GET)
    public List<Camera> getDevices(@RequestParam(value = "customerId",required = true) Integer customerId,
                                   @RequestParam(value = "method",required = false) String method) throws Exception{

        return cameraService.dealGetDevices(customerId, method);
    }


    @RequestMapping(value = "/addDevice", method = RequestMethod.GET)
    public JSONObject addDevice(@RequestBody JSONObject data) throws Exception{
//        String user = data.getString("user");
//        String passwd = data.getString("passwd");
        String serial = data.getString("serial");
        String validateCode = data.getString("validateCode");
        Integer customerId = data.getInteger("customerId");
        String name = data.getString("name");
        String group = data.getString("group");
        String manufature = data.getString("manufacturer");
        String model = data.getString("model");
        String version = data.getString("version");
        String discription = data.getString("description");
        Timestamp lastOnline =new Timestamp(System.currentTimeMillis());
        String state = "online";

        Camera camera = new Camera(serial, name, lastOnline.toString(), state, version, model, manufature, group, discription);

//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);
        return cameraService.addDevice(customerId,serial,validateCode, camera);

    }


//    @RequestMapping(value = "/addDevice", method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject addDevice(@RequestParam(value = "user",required = true) String user,
//                                @RequestParam(value = "passwd",required = true) String passwd,
//                                @RequestParam(value = "serial",required = true) String serial,
//                                @RequestParam(value = "validateCode",required = true) String validateCode) throws Exception{
//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);
//        return cameraService.addDevice(userInfo,serial,validateCode);
//    }

    @RequestMapping(value = "/delDevice", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject delDevice(@RequestParam(value = "customerId",required = true) Integer customerId,
                                @RequestParam(value = "cameraId",required = true) String cameraId,
                                @RequestParam(value = "serial",required = true) String serial) throws Exception{
//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);
        return cameraService.delDevice(customerId,serial, cameraId);
    }


    /**
     * 修改摄像头信息
     * @param data
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateDeviceInfo", method = RequestMethod.POST)
    public JSONObject updateDeviceInfo(@RequestBody JSONObject data) throws Exception{

//        String user = data.getString("user");
//        String passwd = data.getString("passwd");
        String serial = data.getString("serial");
        String validateCode = data.getString("validateCode");
        Integer customerId = data.getInteger("customerId");
        String name = data.getString("name");
        String group = data.getString("group");
        String manufature = data.getString("manufacturer");
        String model = data.getString("model");
        String version = data.getString("version");
        String discription = data.getString("description");
        String state = data.getString("state");
        Timestamp lastOnline =new Timestamp(System.currentTimeMillis());

//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);

        Camera camera = new Camera(serial, name, lastOnline.toString(), state, version, model, manufature, group, discription);
        // TODO
        return cameraService.updateDeviceInfo(camera);
    }

    /**
     * 修改报警参数设置
     * @param data
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/updateAlarmSettings", method = RequestMethod.POST)
//    public JSONObject updateAlarmSettings(@RequestBody JSONObject data) throws Exception{
//
//        String user = data.getString("user");
//        String passwd = data.getString("passwd");
//        String serial = data.getString("serial");
//
//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);
//        return cameraService.updateAlarmSettings(userInfo, serial);
//    }

    /**
     * 获取摄像头截图
     * @param method 排序方式
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/getPhotos", method = RequestMethod.GET)
//    public List<Photo> getPhotos(@RequestParam(value = "user",required = true) String user,
//                                 @RequestParam(value = "passwd",required = true) String passwd,
//                                 @RequestParam(value = "method",required = false) String method) throws Exception{
//
//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);
//        return cameraService.getPhotos(userInfo, method);
//    }

    /**
     * 获取摄像头录屏
     * @param method  排序方式
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/getVideoes", method = RequestMethod.GET)
//    public List<Video> getVideoes(@RequestParam(value = "user",required = true) String user,
//                                 @RequestParam(value = "passwd",required = true) String passwd,
//                                 @RequestParam(value = "method",required = false) String method) throws Exception{
//
//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);
//        return cameraService.getVideoes(userInfo, method);
//    }

    /**
     * 控制报警器
     * @param type 报警类型（声/光/声光）
     * @param state 开关
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/alarm", method = RequestMethod.GET)
//    public List<Video> setAlarm(@RequestParam(value = "user",required = true) String user,
//                                  @RequestParam(value = "passwd",required = true) String passwd,
//                                  @RequestParam(value = "type",required = true) String type,
//                                  @RequestParam(value = "state",required = true) String state) throws Exception{
//
//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);
//        return cameraService.setAlarm(userInfo, type, state);
//    }
}
