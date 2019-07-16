package com.edu.bupt.wechatpost.service.impl;

import com.edu.bupt.wechatpost.dao.DeviceTokenRelationMapper;
import com.edu.bupt.wechatpost.service.DeviceTokenRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceTokenRelationServiceImpl implements DeviceTokenRelationService {
    @Autowired
    private  DeviceTokenRelationMapper deviceTokenRelationMapper;

    @Override
    public String findIEEEByUuid(String uuid) {
        return deviceTokenRelationMapper.selectIEEEByUuid(uuid);
    }

    @Override
    public List<String> findUuidByIEEE(String ieee) {
        return deviceTokenRelationMapper.selectUuidByIEEE(ieee);
    }
}
