package com.edu.bupt.wechatpost.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceTokenRelationMapper {
    String selectIEEEByUuid(@Param("uuid") String uuid);
    List<String> selectUuidByIEEE(@Param("ieee")String ieee);
}
