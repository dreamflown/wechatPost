package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.model.CameraUser;
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



    /*
    *   测试完成
    * */
    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getToken(@RequestParam("customerId")Integer id) throws Exception{
        JSONObject ret = new JSONObject();
        System.out.println(id);
        String result = cameraService.sendForaccessToken(id);
        ret.put("status",result);
        if(result.equals("404")){
            ret.put("msg","用户未注册");
        }else if(result.equals("500")){
            ret.put("msh","内部错误");
        }else{
            ret.put("status","200");
            ret.put("msg",result);
        }
        return ret;
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public JSONObject updateUserInfo(@RequestBody JSONObject userInfo) throws Exception{
        return cameraService.updateUserInfo(userInfo);
    }

    /**
     * 获取摄像头流地址列表   cewanle
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getLiveAddress", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getLiveAddressList(@RequestParam("customerId")Integer id) throws Exception{

        return cameraService.getLiveAddressList(id);
    }

    /**
     * 获取摄像头流地址列表   测试完成
     * @param serial
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getLiveAddressbySerial", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getLiveAddressbySerial(@RequestParam("customerId")Integer id,
                                             @RequestParam("serial")String serial) throws Exception{

        return cameraService.getLiveAddrBydeviceSerial(id,serial,"1");
    }



    /**
     * 打开视频流    ceshiwancheng
     * @param serial
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/openLive", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject openLiveByserial(@RequestParam("customerId")Integer id,
                                        @RequestParam("serial") String serial) throws Exception{

        System.out.println(id);
        System.out.println(serial);
        return cameraService.openLiveBydeviceSerial(id,serial,"1");

    }

    /**
     * 关闭视频流 ceshiwancheng
     * @param serial
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/closeLive", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject closeLiveByserial(@RequestParam("customerId")Integer id,
                                       @RequestParam(value = "serial")String serial) throws Exception{
//        JSONObject userInfo = new JSONObject();
//        userInfo.put("user",user);
//        userInfo.put("passwd",passwd);
        return cameraService.closeLiveBydeviceSerial(id,serial,"1");

    }


    /**
     * 获取设备能力 ceshiwancheng
     * @param serial
     * @return
     * @throws Exception
     */
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
     * 添加摄像头 测试完成
     * @param data
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addDevice", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addDevice(@RequestBody JSONObject data) throws Exception{

        return cameraService.addDevice(data.getInteger("customerId"),data.getString("serial"),
                                        data.getString("validateCode"),data.getString("name"),
                                        data.getString("discription"));
    }


    /**
     * 删除摄像头 测试完成
     * @param serial
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delDevice", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject delDevice(@RequestParam(value = "customerId",required = true) Integer customerId,
                                @RequestParam(value = "serial",required = true) String serial) throws Exception{
        return cameraService.delDevice(customerId,serial);
    }




    /**
     * 获取摄像头列表并排序 ceshiwancheng
     * @param customerId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDevices", method = RequestMethod.GET)
    public JSONObject getDevices(@RequestParam(value = "customerId",required = true) Integer customerId) throws Exception{

        return cameraService.dealGetDevices(customerId);
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


    /**
     * 修改摄像头信息 测试完成
     * @param body
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateDeviceInfo", method = RequestMethod.POST)
    public JSONObject updateDeviceInfo(@RequestBody JSONObject body) throws Exception{

        return cameraService.updateDeviceInfo(body);
    }

    /**
     * 用户注册 测试完成
     * @param data
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSONObject register(@RequestBody JSONObject data) {

        return cameraService.register(data);
    }

    /**
     * 使用序列号获取设备信息
     * @param serial
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDeviceBySerial",method =  RequestMethod.GET)
    public  JSONObject getDeviceBySerial(@RequestParam("customerId")Integer customerId,
                                         @RequestParam("serial")String serial){

        return cameraService.getDeviceBySerial(customerId,serial);
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
