package com.example.sw2.controller.admin;

import com.example.sw2.entity.Inventario;
import com.example.sw2.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/inventario")
public class ListaInventarioController {
    @Autowired
    InventarioRepository inventarioRepository;

    @GetMapping(value = {"", "/"})
    public String listInv(@ModelAttribute("inventario") Inventario inventario, Model model) {
        model.addAttribute("listaInv", inventarioRepository.findAll());
        return "admin/listaInventario";
    }
}
