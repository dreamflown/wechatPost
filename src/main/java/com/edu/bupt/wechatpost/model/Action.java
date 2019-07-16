package com.edu.bupt.wechatpost.model;

public class Action {
    private Integer LIKE;
    private Integer COMMENT;

    public Action(){
        this.LIKE = 1;
        this.COMMENT = 2;
    }

    public Integer getLIKE() {
        return LIKE;
    }

    public Integer getCOMMENT() {
        return COMMENT;
    }
}
