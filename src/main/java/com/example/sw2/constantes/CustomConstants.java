package com.example.sw2.constantes;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.*;


public final class CustomConstants {
    public final static String AWS_BUCKET_NAME = "test-bucket-sw2-1";
    public final static String INVENTARIO = "inventario";
    public final static String PERFIL = "profile";
    public final static int BIGNUMBER = 1749183;


/*
    public String generateS3Link(String  objectName, String folder){
        return "https://"+AWS_BUCKET_NAME+".s3.amazonaws.com/"+FOLDER1+"/"+objectName+".png";
    }*/

    private static final LinkedHashMap<String, String> lineas = new LinkedHashMap<>();
    private static final LinkedHashMap<String, String> meses = new LinkedHashMap<>();
    private static final HashMap<Integer, String> tiposAdquisicion = new HashMap<>();
    private static final HashMap<Integer, String> tiposDocumento = new HashMap<>();
    private static final HashMap<Integer, String> estadoAsignacion = new HashMap<>();
    private static final LinkedHashMap<String, String> tamanhos = new LinkedHashMap<>();
    public static final Calendar calendar = Calendar.getInstance();
    static {
        lineas.put("T", "Tradicional");
        lineas.put("M", "Mosqoy");
        lineas.put("F", "Fibras");

        meses.put("JAN", "Enero");
        meses.put("FEB", "Febrero");
        meses.put("MAR", "Marzo");
        meses.put("APR", "Abril");
        meses.put("MAY", "Mayo");
        meses.put("JUN", "Junio");
        meses.put("JUL", "Julio");
        meses.put("AUG", "Agosto");
        meses.put("SEP", "Septiembre");
        meses.put("OCT", "Octubre");
        meses.put("NOV", "Noviembre");
        meses.put("DEC", "Diciembre");

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

    public static HashMap<Integer, String> getEstadoAsignacion() {
        return estadoAsignacion;
    }

    public static LinkedHashMap<String, String> getLineas() {
        return lineas;
    }

    public static LinkedHashMap<String, String> getMeses() {
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
