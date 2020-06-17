package com.example.sw2.entity;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class RestBean implements Serializable {
    private String key;
    private String name;
    private ByteArrayResource file;

    public RestBean(){

    }

    public RestBean(String key, String name, ByteArrayResource file){
        this.key = key;
        this.file = file;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ByteArrayResource getFile() {
        return file;
    }

    public void setFile(ByteArrayResource file) {
        this.file = file;
    }
}
