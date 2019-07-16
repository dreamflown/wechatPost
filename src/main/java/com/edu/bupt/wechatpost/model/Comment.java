package com.edu.bupt.wechatpost.model;

public class Comment {
    private Integer id;

    private Integer p_id;

    private String nickname;

    private String c_content;


    public Comment(Integer id, Integer p_id, String nickname, String c_content) {
        this.id = id;
        this.p_id = p_id;
        this.nickname = nickname;
        this.c_content = c_content;
    }

    public Comment(Integer p_id, String nickname, String c_content) {
        this.p_id = p_id;
        this.nickname = nickname;
        this.c_content = c_content;
    }

    public Comment() {
        super();
    }

    public Integer getcId() {
        return id;
    }

    public void setcId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return p_id;
    }

    public void setpId(Integer p_id) {
        this.p_id = p_id;
    }

    public String getNickName() {
        return nickname;
    }

    public void setNickName(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getcContent() {
        return c_content;
    }

    public void setcContent(String c_content) {
        this.c_content = c_content == null ? null : c_content.trim();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", p_id=" + p_id +
                ", nickname='" + nickname + '\'' +
                ", c_content='" + c_content + '\'' +
                '}';
    }
}