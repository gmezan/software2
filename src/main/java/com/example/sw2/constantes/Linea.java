package com.example.sw2.constantes;

public enum Linea {
    T("Tradicional"),
    M("Mosqoy"),
    F("Fibras");

    private final String valor;

    Linea(String valor) {
        this.valor = valor;
    }

    public String valor() {
        return valor;
    }
}


