package com.edu.bupt.wechatpost.dao;

import com.edu.bupt.wechatpost.model.CameraUserRelation;

import java.util.List;

public interface CameraUserRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByCustomerIdAndCameraId(Integer customerId, String cameraId);

    int insert(CameraUserRelation record);

    int insertSelective(CameraUserRelation record);

    CameraUserRelation selectByPrimaryKey(Integer id);

    List<String> selectCameraIdByCustomerId(Integer id);

    int updateByPrimaryKeySelective(CameraUserRelation record);

    int updateByPrimaryKey(CameraUserRelation record);
}