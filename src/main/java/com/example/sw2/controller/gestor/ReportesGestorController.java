package com.example.sw2.controller.gestor;

import com.example.sw2.entity.Reportes;
import com.example.sw2.service.IReporteGestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/gestor/reportes")
public class ReportesGestorController {



    @Autowired
    IReporteGestorService IReporteGestorService;


    @GetMapping(value = "")
    public String listaReportes(){
        return "gestor/Reportes";
//        return "gestor/en_construccion";
    }


    @PostMapping(value = "/generate")
    public ResponseEntity<InputStreamResource> printExcel(@RequestParam("ordenar") Integer orderBy, @RequestParam("tipo") Integer type,
                                                          @RequestParam("years") Integer anho, @RequestParam("tipoSelect") Integer select)
                                                            throws Exception{

        Reportes reportes = new Reportes(orderBy, anho, type,select);
        HttpHeaders headers = new HttpHeaders();
        ByteArrayInputStream stream = new  ByteArrayInputStream(new byte[]{});
        String filename;
        if (reportes.validateGestor()){
            stream = IReporteGestorService.generarReporte(reportes);
            filename = reportes.createNameGestor();
        }else filename = "error";

        headers.add("Content-Disposition","attachment; filename="+ filename +".xls");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }

}
