package com.example.sw2.controller.gestor;

import com.example.sw2.service.ServiceReportes;
import com.example.sw2.service.ServiceVentaAnualXxProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/gestor/reportes")
public class ReportesGestorController {


    @Autowired
    ServiceReportes serviceReportes;


    //////inicio prueba excel
    @Autowired
    ServiceVentaAnualXxProducto serviceVentaAnualXxProducto;

    @GetMapping(value = "")
    public String listaReportes(){
        return "gestor/Reportes";
//        return "gestor/en_construccion";
    }

    @GetMapping(value = {"/excelAnualXxProducto"})
    public ResponseEntity<InputStreamResource> excelAnualXxProducto() throws Exception{

        ByteArrayInputStream stream = serviceVentaAnualXxProducto.exportAllData();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment; filename=ventasProducto.xls");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
    //fin rpueba excel




}
