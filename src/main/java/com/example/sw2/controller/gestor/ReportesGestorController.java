package com.example.sw2.controller.gestor;

import com.example.sw2.entity.Reportes;
import com.example.sw2.service.ServiceReportes;
import com.example.sw2.service.ServiceReportes2222;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/gestor/reportes")
public class ReportesGestorController {


    @Autowired
    ServiceReportes serviceReportes;

    @Autowired
    ServiceReportes2222 serviceReportes2222;


    @GetMapping(value = "")
    public String listaReportes(){
        return "gestor/Reportes";
//        return "gestor/en_construccion";
    }


    //fin rpueba excel



    @PostMapping(value = "/save")
    public ResponseEntity<InputStreamResource> printExcel(Reportes reportes)
                                                            throws Exception{

        ByteArrayInputStream stream = serviceReportes2222.generarReporte(reportes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment; filename=ventasProducto.xls");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }


}
