package com.example.sw2.entity;


import java.time.LocalDate;

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

    public String createNameGestor(){
        String n = "Reporte ";

        switch (orderBy){
            case 1:
                n+="Total de Ventas";
                break;
            case 2:
                n+="de Sedes";
                break;
            case 3:
                n+="de Productos";
                break;
            case 4:
                n+="de Comunidades";
                break;
            case 5:
                n+="de Clientes";
                break;
        }

        return n+ LocalDate.now().toString();
    }

    public boolean validateGestor(){
        boolean validate = true;
        if (orderBy<1 || orderBy>5) validate=false;
        return validate && validateGeneral();
    }

    public boolean validateSede(){
        boolean validate = true;
        if (orderBy<1 || orderBy>4) validate=false;
        return validate && validateGeneral();
    }

    private boolean validateGeneral(){
        boolean validate = true;

        if (year<2000 || year>3000) validate=false;
        if (type<1 || type>3)
            validate = false;
        else {
            if (type==2)//Trimestral
            {
                if (selected<1||selected>4) validate=false;
            }
            else if (type==3)//Mensual
            {
                if (selected<1||selected>12) validate=false;
            }
        }

        return  validate;
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
