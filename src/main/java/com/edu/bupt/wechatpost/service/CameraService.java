package com.edu.bupt.wechatpost.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.model.Camera;

import java.util.List;

public interface CameraService {
    String getAccessTocken(Integer customer_id);
    String register(String appKey, String appSecret);
    JSONArray getLiveAddressList(Integer customer_id);
    JSONArray getLiveAddrBydeviceSerial(Integer customer_id,String deviceSerial,String cam);
    JSONArray openLiveBydeviceSerial(Integer customer_id,String deviceSerial,String Cam);
    JSONArray closeLiveBydeviceSerial(Integer customer_id,String deviceSerial,String Cam);
    JSONObject getDevicecapacity(Integer customer_id, String deviceSerial);
    JSONObject addDevice(Integer customer_id, String serial,String validateCode, Camera camera);
    JSONObject delDevice(Integer customer_id,String deviceSerial, String camera_id);
    List<Camera> dealGetDevices(Integer customerId, String method)throws Exception;
    JSONObject updateDeviceInfo(Camera camera);
    int updateAlarmSettings(Integer customer_id, String serial);
    int dealAddDevice(Integer customerId, Camera camera);
    int dealDeleteDevice(Integer customerId, String camera_id);
//    List<Photo> getPhotos(Integer customer_id, String method);
//    List<Video> getVideoes(Integer customer_id, String method);
//    JSONObject setAlarm(Integer customer_id, String type, String state);

}