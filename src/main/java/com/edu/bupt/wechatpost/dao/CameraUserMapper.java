package com.edu.bupt.wechatpost.dao;

import com.edu.bupt.wechatpost.model.CameraUser;

public interface CameraUserMapper {
    int deleteByPrimaryKey(Integer customerId);

    int insert(CameraUser record);

    int insertSelective(CameraUser record);

    CameraUser selectByPrimaryKey(Integer customerId);

    int updateByPrimaryKeySelective(CameraUser record);

    int updateByPrimaryKey(CameraUser record);
}