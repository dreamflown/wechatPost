package com.edu.bupt.wechatpost.model;

import java.io.Serializable;
import java.util.HashMap;

public class Result extends HashMap<String, Object> implements Serializable {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String EXCEPTION = "exception";
    public static final String RESUTLMSG_SUCCESS = "操作成功!";
    public static final String RESUTLMSG_ERROR = "操作失败!";
    public static final String RESUTLMSG_EXCEPTION = "操作异常!";

    public Result() {
        setStatus(SUCCESS);
    }

    public void setStatus(String value) {
        put("status", value);
        switch (value) {
            case SUCCESS: {
                setResultMsg(RESUTLMSG_SUCCESS);
                break;
            }
            case ERROR: {
                setResultMsg(RESUTLMSG_ERROR);
                break;
            }
            case EXCEPTION: {
                setResultMsg(RESUTLMSG_EXCEPTION);
                break;
            }
            default: {
                break;
            }
        }
    }

    public void setData(Object value) {
        put("data", value);
    }

    public void setResultMsg(Object value) {
        put("resultMsg", value);
    }
}
