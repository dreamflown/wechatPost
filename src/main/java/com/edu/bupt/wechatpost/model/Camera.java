package com.edu.bupt.wechatpost.model;

public class Camera {
    private String id;

    private String serial;

    private String name;

    private String lastOnline;

    private String state;

    private String version;

    private String model;

    private String manufature;

    private String group;

    private String store;

    private String discription;

    public Camera(String id, String serial, String name, String lastOnline, String state, String version, String model, String manufature, String group, String store, String discription) {
        this.id = id;
        this.serial = serial;
        this.name = name;
        this.lastOnline = lastOnline;
        this.state = state;
        this.version = version;
        this.model = model;
        this.manufature = manufature;
        this.group = group;
        this.store = store;
        this.discription = discription;
    }

    public Camera(String serial, String name, String lastOnline, String state, String version, String model, String manufature, String group, String discription) {
        this.serial = serial;
        this.name = name;
        this.lastOnline = lastOnline;
        this.state = state;
        this.version = version;
        this.model = model;
        this.manufature = manufature;
        this.group = group;
        this.discription = discription;
    }

    public Camera() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial == null ? null : serial.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(String lastOnline) {
        this.lastOnline = lastOnline == null ? null : lastOnline.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getManufature() {
        return manufature;
    }

    public void setManufature(String manufature) {
        this.manufature = manufature == null ? null : manufature.trim();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group == null ? null : group.trim();
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store == null ? null : store.trim();
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription == null ? null : discription.trim();
    }
}