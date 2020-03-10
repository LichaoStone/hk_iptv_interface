package com.hicon.v1.hotword.bean;

import com.hicon.frame.BaseEntity;

import java.util.List;

public class HotwordBean extends BaseEntity {
    private String tHotKey;
    private String hotWords;

    private List<String> keyList;

    public String gettHotKey() {
        return tHotKey;
    }

    public void settHotKey(String tHotKey) {
        this.tHotKey = tHotKey;
    }

    public String getHotWords() {
        return hotWords;
    }

    public void setHotWords(String hotWords) {
        this.hotWords = hotWords;
    }

    public List<String> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }
}
