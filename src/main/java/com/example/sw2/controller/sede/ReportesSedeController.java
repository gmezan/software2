package com.example.sw2.controller.sede;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sede/reportes")
public class ReportesSedeController {


    @Autowired
    DataResourceHandler dataResourceHandler;

    @GetMapping(value = "")
    public String listaReportes(){
        return "sede/reportesSede";
//        return "sede/reportesSedeEnConstruccion";
    }



    @GetMapping("/excel")
    public String generarExcel(RedirectAttributes attr){
        dataResourceHandler.ReportLastMonth(1);
        attr.addFlashAttribute("msg","  Excel creado");
        System.out.println("llego al final");
        return "redirect:/sede/reportes";
    }



}
