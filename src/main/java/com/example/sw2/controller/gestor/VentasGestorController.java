package com.example.sw2.controller.gestor;

import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gestor/ventas")
public class VentasGestorController {

    @Autowired
    VentasRepository ventasRepository;

    @GetMapping(value = {"", "/"})
    public String listVen(Model model) {
        model.addAttribute("lista", ventasRepository.obtenerDatosGestorVentas());
        return "gestor/ventas";
    }
}
