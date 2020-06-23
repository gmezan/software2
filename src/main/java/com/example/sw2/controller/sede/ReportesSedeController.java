package com.example.sw2.controller.sede;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/sede/reportes")
public class ReportesSedeController {

    @GetMapping(value = "")
    public String listaReportes(){
//        return "sede/reportesSede";
        return "sede/reportesSedeEnConstruccion";
    }



    @GetMapping("/excel")
    public String generarExcel(RedirectAttributes attr){
        GenerateExcel generateExcel = new GenerateExcel();
        generateExcel.newExcel();
        attr.addFlashAttribute("msg","  Excel creado");
        System.out.println("llego al final");
        return "redirect:/product";
    }
    
}
