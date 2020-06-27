package com.example.sw2.service;

import com.example.sw2.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportesService implements ServiceReportes{

    @Autowired
    VentasRepository ventasRepository;


}
