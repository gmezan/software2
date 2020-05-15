package com.example.sw2.constantes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public final class CustomConstants {

    private static final HashMap<Integer, String> lineas = new HashMap<>();
    private static final HashMap<Integer, List<String>> meses = new HashMap<>();
    private static final HashMap<Integer, String> tiposAdquisicion = new HashMap<>();
    private static final HashMap<Integer, String> tiposDocumento = new HashMap<>();
    private static final LinkedHashMap<String, String> tamanhos = new LinkedHashMap<>();

    static {
        lineas.put(1, "Tradicional");
        lineas.put(2, "Mosqoy");
        lineas.put(3, "Fibras");


        List<String> jan = new ArrayList<>();
        jan.add("JAN");
        jan.add("Enero");
        List<String> feb = new ArrayList<>();
        feb.add("FEB");
        feb.add("Febrero");
        List<String> mar = new ArrayList<>();
        mar.add("MAR");
        mar.add("Marzo");
        List<String> apr = new ArrayList<>();
        apr.add("APR");
        apr.add("Abril");
        List<String> may= new ArrayList<>();
        may.add("MAY");
        may.add("Mayo");

        meses.put(1,jan);
        meses.put(2,feb);
        meses.put(3,mar);
        meses.put(4,apr);
        meses.put(5,may);

        tamanhos.put("OS","One-Size");
        tamanhos.put("S","Small");
        tamanhos.put("M","Medium");
        tamanhos.put("L","Large");

        tiposAdquisicion.put(0,"Comprado");
        tiposAdquisicion.put(1,"Consignado");


    }


    public static HashMap<Integer, String> getLineas() {
        return lineas;
    }

    public static HashMap<Integer, List<String>> getMeses() {
        return meses;
    }

    public static LinkedHashMap<String, String> getTamanhos() {
        return tamanhos;
    }

    public static HashMap<Integer, String> getTiposAdquisicion() {
        return tiposAdquisicion;
    }
}
