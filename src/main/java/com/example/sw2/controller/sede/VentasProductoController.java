package com.example.sw2.controller.sede;

import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.UsuariosRepository;
import com.example.sw2.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/sede/ventasProducto")
public class VentasProductoController {

    @Autowired
    VentasRepository ventasRepository;
    @Autowired
    UsuariosRepository usuariosRepository;

    @GetMapping(value = {"", "ListaVentasProductos"})
    public String ListaVentasCliente(Model model){

        model.addAttribute("listaProductos", ventasRepository.obtenerDatosProducto());
        model.addAttribute("listaUsuarios", usuariosRepository.findAll());
        model.addAttribute("listaVentas", ventasRepository.findAll());
        return "sede/ListaProductosVendidos";
    }


}
