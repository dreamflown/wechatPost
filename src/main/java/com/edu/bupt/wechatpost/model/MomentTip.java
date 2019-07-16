package com.edu.bupt.wechatpost.model;

import java.sql.Timestamp;

public class MomentTip {
    private Integer id;
    private Timestamp createTime;
    private String avator;
    private String touser;
    private Integer action_id;
    private Integer action_type;
    private Integer isread;

    @Override
    public String toString() {
        return "MomentTip{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", avator='" + avator + '\'' +
                ", touser='" + touser + '\'' +
                ", action_id=" + action_id +
                ", action_type=" + action_type +
                ", isread=" + isread +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public Integer getAction_id() {
        return action_id;
    }

    public void setAction_id(Integer action_id) {
        this.action_id = action_id;
    }

    public Integer getAction_type() {
        return action_type;
    }

    public void setAction_type(Integer action_type) {
        this.action_type = action_type;
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }
}
