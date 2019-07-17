package com.edu.bupt.wechatpost.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.model.Camera;
import com.edu.bupt.wechatpost.model.CameraUser;

import java.util.List;

public interface CameraService {

    boolean validAccessToken(CameraUser user);
    CameraUser getAccessToken(Integer customerId);
    JSONObject updateUserInfo(JSONObject userInfo);
    String sendForaccessToken(Integer customerId);
    String sendForaccessToken(Integer customerId,String appKey,String appSecret);
    JSONObject register(JSONObject userJson);
    JSONObject getLiveAddressList(Integer customer_id);
    JSONObject getLiveAddrBydeviceSerial(Integer customer_id,String deviceSerial,String cam);
    JSONObject openLiveBydeviceSerial(Integer customer_id,String deviceSerial,String Cam);
    JSONObject closeLiveBydeviceSerial(Integer customer_id,String deviceSerial,String Cam);
    JSONObject getDevicecapacity(Integer customer_id, String deviceSerial);
    JSONObject addDevice(Integer customer_id, String serial,String validateCode, String name,String discription);
    JSONObject delDevice(Integer customer_id,String serial);
    JSONObject dealGetDevices(Integer customerId);
    JSONObject updateDeviceInfo(JSONObject cameraJson);
    JSONObject getDeviceBySerial(Integer customerId,String serial);
    int updateAlarmSettings(Integer customer_id, String serial);
    int dealAddDevice(Integer customerId, Camera camera);
    int dealDeleteDevice(Integer customerId, String camera_id);
//    List<Photo> getPhotos(Integer customer_id, String method);
//    List<Video> getVideoes(Integer customer_id, String method);
//    JSONObject setAlarm(Integer customer_id, String type, String state);

}
