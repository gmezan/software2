package com.example.sw2.service;

import java.io.ByteArrayInputStream;

public interface ServiceReportes {


    ////// VENTAS GENERALES POR ANHO
    ByteArrayInputStream obtenerVentasGENERALESPorAnho(int anho) throws Exception;
    //////


    ////// VENTAS DE COMUNIDAD POR ANHO
    ByteArrayInputStream obtenerVentasDeComunidadPorAnho(int anho) throws Exception;
    //////

    ////// VENTAS DE COMUNIDAD POR ANHO EN UN SEMESTRE ESPECIFICO
    ByteArrayInputStream obtenerVentasDeComunidadDelPRIMERTrimestre(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDelSEGUNDOTrimestre(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDelTERCERTrimestre(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDelCUARTOTrimestre(int anho) throws Exception;
    //////

    ////// VENTAS DE COMUNIDAD POR ANHO EN UN MES ESPECIFICO
    ByteArrayInputStream obtenerVentasDeComunidadDeENERO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDeFEBRERO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDeMARZO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDeABRIL(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDeMAYO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDeJUNIO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDeJULIO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDeAGOSTO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDeSETIEMBRE(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDeOCTUBRE(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDeNOVIEMBRE(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeComunidadDeDICIEMBRE(int anho) throws Exception;
    //////

    ////// VENTAS DE PRODUCTO POR ANHO
    ByteArrayInputStream obtenerVentasDeProductoPorAnho(int anho) throws Exception;
    //////

    ////// VENTAS DE PRODUCTO POR ANHO EN UN SEMESTRE ESPECIFICO
    ByteArrayInputStream obtenerVentasDeProductoDelPRIMERTrimestre(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDelSEGUNDOTrimestre(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDelTERCERTrimestre(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDelCUARTOTrimestre(int anho) throws Exception;
    //////

    ////// VENTAS DE PRODUCTO POR ANHO EN UN MES ESPECIFICO
    ByteArrayInputStream obtenerVentasDeProductoDeENERO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDeFEBRERO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDeMARZO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDeABRIL(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDeMAYO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDeJUNIO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDeJULIO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDeAGOSTO(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDeSETIEMBRE(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDeOCTUBRE(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDeNOVIEMBRE(int anho) throws Exception;

    ByteArrayInputStream obtenerVentasDeProductoDeDICIEMBRE(int anho) throws Exception;
    //////




}
