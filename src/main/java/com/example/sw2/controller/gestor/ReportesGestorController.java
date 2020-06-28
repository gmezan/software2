package com.example.sw2.controller.gestor;

import com.example.sw2.service.ServiceReportes;
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

@Controller
@RequestMapping("/gestor/reportes")
public class ReportesGestorController {


    @Autowired
    ServiceReportes serviceReportes;

    @Autowired
    ServiceReportes2222 serviceReportes2222;

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



    @PostMapping(value = "/save")
    public ResponseEntity<InputStreamResource> printExcel(@RequestParam("ordenar") int orderBy, @RequestParam("tipo") int type,
                                                          @RequestParam("years") int anho, @RequestParam("tipoSelect") int Select)
                                                            throws Exception{

        int trimester = 0;
        int mes = 0;
        if (type == 1){
            trimester = 0;
            mes = 0;
        }else if (type == 2){
            trimester = Select;
            mes = 0;
        }else if(type == 3){
            trimester = 0;
            mes = Select;
        }

        System.out.println("El orderBy es: " + orderBy);
        ByteArrayInputStream stream = serviceReportes2222.SedeOrClienteXxAnual_TrimesterOrMonth(mes, trimester, anho, orderBy, type);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment; filename=ventasProducto.xls");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }


}
