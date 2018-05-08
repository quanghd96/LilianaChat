package com.quang.lilianachat.model;

public class ItemChat {
    private String idFirebase;
    private String idFacebook;
    private String icon;
    private String name;
    private long time;
    private String lastMessage;

    public ItemChat() {
    }

    public ItemChat(String idFirebase, String idFacebook, String icon, String name, long time, String lastMessage) {
        this.idFirebase = idFirebase;
        this.idFacebook = idFacebook;
        this.icon = icon;
        this.name = name;
        this.time = time;
        this.lastMessage = lastMessage;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
