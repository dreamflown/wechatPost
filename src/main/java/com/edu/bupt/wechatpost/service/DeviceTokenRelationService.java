package com.edu.bupt.wechatpost.service;

import java.util.List;

public interface DeviceTokenRelationService {
    String findIEEEByUuid(String uuid);
    List<String> findUuidByIEEE(String ieee);
}
