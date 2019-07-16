package com.edu.bupt.wechatpost.model;

public class CameraUserRelation {
    private Integer id;

    private String cameraId;

    private Integer customerId;

    public CameraUserRelation(Integer id, String cameraId, Integer customerId) {
        this.id = id;
        this.cameraId = cameraId;
        this.customerId = customerId;
    }

    public CameraUserRelation(String cameraId, Integer customerId) {
        this.cameraId = cameraId;
        this.customerId = customerId;
    }

    public CameraUserRelation() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId == null ? null : cameraId.trim();
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}