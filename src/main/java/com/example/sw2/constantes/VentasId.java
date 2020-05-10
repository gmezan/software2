package com.example.sw2.constantes;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VentasId implements Serializable {


    @Column(name = "tipodocumento")
    private int tipodocumento;
    @Column(name = "numerodocumento")
    private String numerodocumento;

    public int getTipodocumento() {
        return tipodocumento;
    }

    public VentasId(){

    }

    public VentasId(int x, String y){
        this.setTipodocumento(x);
        this.setNumerodocumento(y);
    }


    public void setTipodocumento(int tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }
}
