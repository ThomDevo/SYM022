package com.sym022.sym022.mail;

import java.util.ArrayList;
import java.util.List;

public class Mail {
    private static final long serialVersionUID = 1L;

    private String msgBody;
    private String from;
    private String nick;
    private String subject;
    private String replyTo;
    private boolean encodeUTF8;
    private String filename;
    private String source;
    private List<String> listTo = new ArrayList<String>();

    /**
     * Default constructor
     */
    public Mail() {
        // TODO Auto-generated constructor stub
    }

    // Getters and Setters

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public boolean isEncodeUTF8() {
        return encodeUTF8;
    }

    public void setEncodeUTF8(boolean encodeUTF8) {
        this.encodeUTF8 = encodeUTF8;
    }

    public String getFilename() { return this.filename; }

    public void setFilename(String filename) { this.filename = filename; }

    public String getSource() {return this.source; }

    public void setSource(String source) { this.source = source; }

    public List<String> getListTo() {
        return listTo;
    }

    public void setListTo(List<String> listTo) {
        this.listTo = listTo;
    }

}
