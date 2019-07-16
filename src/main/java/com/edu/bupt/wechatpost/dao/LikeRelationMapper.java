package com.edu.bupt.wechatpost.dao;

import com.edu.bupt.wechatpost.model.LikeRelation;
import org.apache.ibatis.annotations.Param;

public interface LikeRelationMapper {
    LikeRelation selectById(@Param("id") Integer id);
    LikeRelation selectByOpenidAndPid(@Param("openid") String openid, @Param("p_id") Integer p_id);
    Integer insert(LikeRelation lr);
    void delete(@Param("openid") String openid, @Param("p_id") Integer p_id);
}
