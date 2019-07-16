package com.edu.bupt.wechatpost.model;

public class LikeRelation {
    private Integer id;
    private Integer p_id;
    private String nickname;
    private String openid;

    public LikeRelation(Integer p_id, String nickname){
        this.p_id = p_id;
        this.nickname = nickname;
    }

    public LikeRelation(){};

    @Override
    public String toString() {
        return "LikeRelation{" +
                "id=" + id +
                ", p_id=" + p_id +
                ", nickname='" + nickname + '\'' +
                ", openid='" + openid + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getP_id() {
        return p_id;
    }

    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
