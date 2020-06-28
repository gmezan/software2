package com.example.sw2.controller.gestor;

import com.example.sw2.entity.Reportes;
import com.example.sw2.service.ServiceReportes2222;
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
import java.time.LocalDate;

@Controller
@RequestMapping("/gestor/reportes")
public class ReportesGestorController {



    @Autowired
    ServiceReportes2222 serviceReportes2222;


    @GetMapping(value = "")
    public String listaReportes(){
        return "gestor/Reportes";
//        return "gestor/en_construccion";
    }


    @PostMapping(value = "/generate")
    public ResponseEntity<InputStreamResource> printExcel(@RequestParam("ordenar") Integer orderBy, @RequestParam("tipo") Integer type,
                                                          @RequestParam("years") Integer anho, @RequestParam("tipoSelect") Integer select)
                                                            throws Exception{

        ByteArrayInputStream stream = serviceReportes2222.generarReporte(new Reportes(orderBy,type,anho,select));

        System.out.println("ordenar: "+ orderBy);
        System.out.println("tipo: "+type);
        System.out.println("years: "+ anho);
        System.out.println("tipoSelect: "+ select);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment; filename=reporte "+LocalDate.now().toString() +".xls");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }

}
