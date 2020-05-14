package com.example.sw2.constantes;

import java.awt.*;
import java.lang.reflect.Array;

public enum Tamanho {
    OS("One-Size"),
    S("Small"),
    M("Medium"),
    L("Large");

    private final String valor;

    Tamanho(String valor) {
        this.valor = valor;
    }

    public String valor() {
        return valor;
    }


}
