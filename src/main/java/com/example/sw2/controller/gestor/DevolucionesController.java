package com.example.sw2.controller.gestor;

import com.example.sw2.entity.AsignacionTiendas;
import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.AsignadosSedesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/gestor/devoluciones")
public class DevolucionesController {

    @Autowired
    AsignadosSedesRepository asignadosSedesRepository;


    @GetMapping(value = {"", "/"})
    public String ListaDevoluciones(Model model){

        model.addAttribute("devueltos", asignadosSedesRepository.DatosDevolucion(4));
        return "gestor/devoluciones";
    }

    @PostMapping("/confirmar")
    public String Confirmar(){
        return "redirect:/devoluciones/";
    }
}
