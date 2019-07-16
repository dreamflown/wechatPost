package com.edu.bupt.wechatpost.dao;

import com.edu.bupt.wechatpost.model.Auth;
import org.apache.ibatis.annotations.Param;

public interface AuthMapper {
    String selectOAByUnionid(@Param("oa_openid")String oa_openid);
    Auth selectByUnionid(@Param("unionid")String unionid);
    void insertSelective(Auth user);
    void updateMiniOpenid(@Param("unionid")String unionid, @Param("mini_openid") String mini_openid);
    void deleteByUnionid(@Param("unionid")String unionid);
}
