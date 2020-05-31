package com.example.sw2.constantes;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.*;


public final class CustomConstants {




    public final static String AWS_BUCKET_NAME = "test-bucket-sw2-1";
    public final static String INVENTARIO = "inventario";
    public final static String PERFIL = "profile";
    public final static int BIGNUMBER = 1749183;



    private static final LinkedHashMap<String, String> lineas = new LinkedHashMap<>();
    private static final HashMap<Integer, String> meses = new HashMap<>();
    private static final HashMap<Integer, String> tiposAdquisicion = new HashMap<>();
    private static final HashMap<Integer, String> tiposDocumento = new HashMap<>();
    private static final HashMap<Integer, String> estadoAsignacion = new HashMap<>();
    private static final LinkedHashMap<String, String> tamanhos = new LinkedHashMap<>();

    static {
        lineas.put("T", "Tradicional");
        lineas.put("M", "Mosqoy");
        lineas.put("F", "Fibras");

        meses.put(1,"JAN");
        meses.put(2,"FEB");
        meses.put(3,"MAR");
        meses.put(4,"APR");
        meses.put(5,"MAY");
        meses.put(6,"JUN");
        meses.put(7,"JUL");
        meses.put(8,"AUG");
        meses.put(9,"SEP");
        meses.put(10,"OCT");
        meses.put(11,"NOV");
        meses.put(12,"DEC");

        tamanhos.put("OS", "One-Size");
        tamanhos.put("S", "Small");
        tamanhos.put("M", "Medium");
        tamanhos.put("L", "Large");

        tiposAdquisicion.put(0, "Comprado");
        tiposAdquisicion.put(1, "Consignado");

        tiposDocumento.put(1, "Factura");
        tiposDocumento.put(2, "Boleta");
        tiposDocumento.put(3, "Recibo");
        tiposDocumento.put(4, "Transacción");

        estadoAsignacion.put(1, "Enviado a Sede");
        estadoAsignacion.put(2, "Recibido por Sede");
        estadoAsignacion.put(3, "Recibido con Problemas");
        estadoAsignacion.put(4, "Devuelto por Sede");

    }

    public final static int ESTADO_ENVIADO_A_SEDE=1;
    public final static int ESTADO_RECIBIDO_POR_SEDE=2;
    public final static int ESTADO_RECIBIDO_CON_PROBLEMAS=3;
    public final static int ESTADO_DEVUELTO_POR_SEDE=4;



    public static HashMap<Integer, String> getEstadoAsignacion() {
        return estadoAsignacion;
    }

    public static LinkedHashMap<String, String> getLineas() {
        return lineas;
    }

    public static HashMap<Integer, String> getMeses() {
        return meses;
    }

    public static LinkedHashMap<String, String> getTamanhos() {
        return tamanhos;
    }

    public static HashMap<Integer, String> getTiposAdquisicion() {
        return tiposAdquisicion;
    }

    public static HashMap<Integer, String> getTiposDocumento() {
        return tiposDocumento;
    }
}
