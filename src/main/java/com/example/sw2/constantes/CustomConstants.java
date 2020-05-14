package com.example.sw2.constantes;

import java.util.HashMap;

public final class CustomConstants {
    public final static String AWS_BUCKET_NAME="test-bucket-sw2-1";
    public final static String INVENTARIO = "inventario";
    public final static String PERFIL = "profile";
    public final static int BIGNUMBER = 1749183;


/*
    public String generateS3Link(String  objectName, String folder){
        return "https://"+AWS_BUCKET_NAME+".s3.amazonaws.com/"+FOLDER1+"/"+objectName+".png";
    }*/

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
