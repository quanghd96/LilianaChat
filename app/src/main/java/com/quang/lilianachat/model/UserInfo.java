package com.quang.lilianachat.model;

public class UserInfo {
    private String name;
    private String idFacebook;
    private String idFirebase;
    private String status;
    private long heart;
    private long angry;
    private long countStranger;

    public UserInfo() {
    }

    public UserInfo(String name, String idFacebook, String idFirebase, String status, long heart, long angry, long countStranger) {
        this.name = name;
        this.idFacebook = idFacebook;
        this.idFirebase = idFirebase;
        this.status = status;
        this.heart = heart;
        this.angry = angry;
        this.countStranger = countStranger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getHeart() {
        return heart;
    }

    public void setHeart(long heart) {
        this.heart = heart;
    }

    public long getAngry() {
        return angry;
    }

    public void setAngry(long angry) {
        this.angry = angry;
    }

    public long getCountStranger() {
        return countStranger;
    }

    public void setCountStranger(long countStranger) {
        this.countStranger = countStranger;
    }
}
