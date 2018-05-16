package com.quang.lilianachat.model;

public class Message {
    private String name;
    private String idFirebase;
    private String linkAvatar;
    private long timeSend;
    private String message;
    private String image;

    public Message() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Message(String name, String idFirebase, String linkAvatar, long timeSend, String message, String image) {

        this.name = name;
        this.idFirebase = idFirebase;
        this.linkAvatar = linkAvatar;
        this.timeSend = timeSend;
        this.message = message;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }

    public long getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(long timeSend) {
        this.timeSend = timeSend;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
