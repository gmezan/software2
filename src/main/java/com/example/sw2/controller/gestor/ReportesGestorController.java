package com.example.sw2.controller.gestor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gestor/reportes")
public class ReportesGestorController {

    @GetMapping(value = "")
    public String listaReportes(){
//        return "gestor/Reportes";
        return "gestor/en_construccion";
    }
}
