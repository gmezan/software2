package com.example.sw2.controller.sede;

import com.example.sw2.entity.Reportes;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.service.IReporteSedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/sede/reportes")
public class ReportesSedeController {


    @Autowired
    IReporteSedeService IReporteSedeService;

    @Autowired


    @GetMapping(value = "")
    public String listaReportes(){
        return "sede/reportesSede";
//        return "sede/reportesSedeEnConstruccion";
    }



    @PostMapping(value = "/generate")
    public ResponseEntity<InputStreamResource> printExcel(@RequestParam("ordenar") Integer orderBy, @RequestParam("tipo") Integer type,
                                                          @RequestParam("years") Integer anho, @RequestParam("tipoSelect") Integer Select,
                                                          HttpSession session) throws Exception{

        Usuarios sede = (Usuarios) session.getAttribute("usuario");
        Reportes reportes = new Reportes(orderBy,anho,type,Select);
        HttpHeaders headers = new HttpHeaders();
        ByteArrayInputStream stream = new  ByteArrayInputStream(new byte[]{});
        String filename;
        if (reportes.validateSede()) {
            stream = IReporteSedeService.generarReporte(reportes, sede.getIdusuarios());
            filename = reportes.createNameGestor();
        }else filename="error";

        headers.add("Content-Disposition","attachment; filename="+filename+".xls");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }



}
