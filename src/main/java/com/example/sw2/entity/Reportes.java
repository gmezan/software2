package com.example.sw2.entity;



public class Reportes {

    private Integer orderBy;
    private Integer year;
    private Integer type;
    private Integer selected;


    public Reportes(){}

    public Reportes(Integer orderBy, Integer year, Integer type, Integer selected){
        this.orderBy = orderBy;
        this.year = year;
        this.type = type;
        this.selected = selected;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }


}
