package com.example.sw2.controller.sede;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sede/reportes")
public class ReportesSedeController {

    @GetMapping(value = "")
    public String listaReportes(){
//        return "sede/Reportes";
        return "sede/reportesSedeEnConstruccion";
    }
}
