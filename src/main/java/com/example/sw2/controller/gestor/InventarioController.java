package com.example.sw2.controller.gestor;

import com.example.sw2.entity.Categorias;
import com.example.sw2.entity.Inventario;
import com.example.sw2.repository.CategoriasRepository;
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


    @GetMapping(value = {"", "/"})
    public String listInv(@ModelAttribute("inventario") Inventario inventario, Model model) {
        model.addAttribute("listaInv", inventarioRepository.findAll());
        return "gestor/inventarioGestor";
    }


}
