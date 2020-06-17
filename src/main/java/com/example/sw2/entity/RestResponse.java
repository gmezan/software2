package com.example.sw2.entity;

public class RestResponse {

    private String fileName;
    private String url;
    private String status;


    public RestResponse(){}

    public RestResponse(String fileName, String url, String status){
        this.fileName = fileName;
        this.url = url;
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
