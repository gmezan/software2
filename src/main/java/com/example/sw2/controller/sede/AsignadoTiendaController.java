package com.example.sw2.controller.sede;

import com.example.sw2.repository.AsignacionTiendasRepository;
import com.example.sw2.repository.UsuariosRepository;
import com.example.sw2.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sede/AsignadoTienda")
public class AsignadoTiendaController {

    @Autowired
    AsignacionTiendasRepository asignacionTiendasRepository;


    @GetMapping(value = {"", "ListaAsignacionTinedas"})
    public String ListaAsignacionTiendas(Model model){

        model.addAttribute("listaDatosAsignados", asignacionTiendasRepository.obtenerDatosAsignados());
        return "sede/asignadoTiendas";
    }
}
