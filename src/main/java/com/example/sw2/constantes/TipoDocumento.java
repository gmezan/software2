package com.example.sw2.constantes;

public enum TipoDocumento {

    F("Factura"),
    B("Boleta"),
    R("Recibo"),
    T("Transacción");

    private  final String valor;

    TipoDocumento(String valor){
        this.valor=valor;
    }

    public String valor() {
        return valor;
    }
}
