package com.example.sw2.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class RestBean implements Serializable {
    private String key;
    private String name;
    @JsonSerialize
    private MultipartFile file;

    public RestBean(){

    }

    public RestBean(String key, String name, MultipartFile file){
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
