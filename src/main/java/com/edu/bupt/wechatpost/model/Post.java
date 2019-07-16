package com.edu.bupt.wechatpost.model;

import java.util.List;

public class Post {
    private Integer id;

    private String openid;

    private String avatar;

    private String nickname;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createTime='" + createTime + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", location='" + location + '\'' +
                ", favoriteNum=" + favoriteNum +
                ", comments=" + comments +
                '}';
    }

    private String createTime;

    private String content;

    private String image;

    private String location;

    private Integer favoriteNum;

    private List<Comment> comments;

    public Post(Integer id, String openid, String avatar, String nickname, String createTime, String content, String image, String location, Integer favoriteNum) {
        this.id = id;
        this.openid = openid;
        this.avatar = avatar;
        this.nickname = nickname;
        this.createTime = createTime;
        this.content = content;
        this.image = image;
        this.location = location;
        this.favoriteNum = favoriteNum;
    }

    public Post(String openid, String avatar, String nickname, String createTime, String content, String image, String location, Integer favoriteNum) {
        this.openid = openid;
        this.avatar = avatar;
        this.nickname = nickname;
        this.createTime = createTime;
        this.content = content;
        this.image = image;
        this.location = location;
        this.favoriteNum = favoriteNum;
    }

    public Post() {
        super();
    }

    public Integer getpId() {
        return id;
    }

    public void setpId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openid;
    }

    public void setOpenId(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getpAvatar() {
        return avatar;
    }

    public void setpAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getNickName() {
        return nickname;
    }

    public void setNickName(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getTimeStamp() {
        return createTime;
    }

    public void setTimeStamp(String createTime) {
        this.createTime = createTime;
    }

    public String getpContent() {
        return content;
    }

    public void setpContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getFavoritenum() {
        return favoriteNum;
    }

    public void setFavoritenum(Integer favoriteNum) {
        this.favoriteNum = favoriteNum;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}