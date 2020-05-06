package com.example.sw2.constantes;

public enum Mes {
    JAN ("Enero"),
    FEB ("Febrero"),
    MAR ("Marzo"),
    APR ("Abril"),
    MAY ("Mayo"),
    JUN ("Junio"),
    JUL ("Julio"),
    AUG ("Agosto"),
    SEP ("Septiembre"),
    OCT ("Octubre"),
    NOV ("Noviembre"),
    DEC ("Diciembre");

    private final String valor;

    Mes(String valor) {
        this.valor = valor;
    }
    public String valor() { return valor; }



}
