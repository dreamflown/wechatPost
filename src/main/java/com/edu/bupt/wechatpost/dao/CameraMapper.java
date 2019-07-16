package com.edu.bupt.wechatpost.dao;

import com.edu.bupt.wechatpost.model.Camera;

public interface CameraMapper {
    int deleteByPrimaryKey(String id);

    int insert(Camera record);

    int insertSelective(Camera record);

    Camera selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Camera record);

    int updateByPrimaryKeyWithBLOBs(Camera record);

    int updateByPrimaryKey(Camera record);
}