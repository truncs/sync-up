package com.syncup.service.core;

/**
 * Created with IntelliJ IDEA.
 * User: aditya
 * Date: 4/20/12
 * Time: 5:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class Saying {
    private final long id;
    private final String content;

    public Saying(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
