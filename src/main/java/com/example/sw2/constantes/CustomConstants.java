package com.example.sw2.constantes;

import java.util.HashMap;

public final class CustomConstants {

    private static final HashMap<Integer, String> lineas = new HashMap<>();
    private static final HashMap<Integer,String> MESES = new HashMap<>();
    static {
        lineas.put(1, "Tradicional");
        lineas.put(2, "Mosqoy");
        lineas.put(3, "Fibras");

        MESES.put(1,"JAN");
    }


    public static HashMap<Integer, String> getLineas() {
        return lineas;
    }

}
