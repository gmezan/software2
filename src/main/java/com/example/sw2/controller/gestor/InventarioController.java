package com.example.sw2.controller.gestor;

import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.entity.Inventario;
import com.example.sw2.repository.CategoriasRepository;
import com.example.sw2.repository.ComunidadesRepository;
import com.example.sw2.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gestor/inventario")
public class InventarioController {
    @Autowired
    InventarioRepository inventarioRepository;
    @Autowired
    CategoriasRepository categoriasRepository;

    @Autowired
    ComunidadesRepository comunidadesRepository;


    @GetMapping(value = {"", "/"})
    public String listInv(@ModelAttribute("inventario") Inventario inventario, Model m) {
        m.addAttribute("listaInv", inventarioRepository.findAll());

        return "gestor/inventarioGestor";
    }

    @GetMapping(value = {"/form"})
    public String form(@ModelAttribute("inventario") Inventario inventario, Model m) {

        m.addAttribute("Meses", CustomConstants.getMeses());
        m.addAttribute("taman", CustomConstants.getTamanhos());
        m.addAttribute("tipoAdqui", CustomConstants.getTiposAdquisicion());
        m.addAttribute("lineas", CustomConstants.getLineas());
        m.addAttribute("listCat", categoriasRepository.findAll());
        m.addAttribute("listCom", comunidadesRepository.findAll());
        return "gestor/inventarioGestorForm";
    }

}
