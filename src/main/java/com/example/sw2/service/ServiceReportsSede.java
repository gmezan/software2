package com.example.sw2.service;

import com.example.sw2.entity.Reportes;

import java.io.ByteArrayInputStream;

public interface ServiceReportsSede {

    ByteArrayInputStream generarReporte(Reportes reportes) throws Exception;

}
