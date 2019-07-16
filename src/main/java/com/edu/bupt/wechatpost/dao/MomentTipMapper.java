package com.edu.bupt.wechatpost.dao;

import com.edu.bupt.wechatpost.model.MomentTip;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MomentTipMapper {
    List<MomentTip> selectById(@Param("id")Integer id);
    List<MomentTip> selectByOpenid(@Param("touser") String touser);
    List<MomentTip> selectUnreadByOpenid(@Param("touser") String touser);
    void updateIsread(@Param("touser")String touser, @Param("isread")Integer isread);
    void insert(MomentTip tip);
    void delete(@Param("touser")String touser, @Param("action_id")Integer action_id);
}
