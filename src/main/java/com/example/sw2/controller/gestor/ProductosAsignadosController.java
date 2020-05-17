package com.example.sw2.controller.gestor;

import com.example.sw2.repository.AsignadosSedesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gestor/asignado")
public class ProductosAsignadosController {

    @Autowired
    AsignadosSedesRepository asignadosSedesRepository;

    @GetMapping("")
    public String ListarProductosAsignados(Model model){
        model.addAttribute("listaAsignados",asignadosSedesRepository.findAll());
        return "gestor/asignadosSedes";
    }
}
