package com.example.sw2.controller.sede;

import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.UsuariosRepository;
import com.example.sw2.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/sede/ventasProducto")
public class VentasProductoController {

    @Autowired
    VentasRepository ventasRepository;
    @Autowired
    UsuariosRepository usuariosRepository;

    @GetMapping(value = {"", "ListaVentasProductos"})
    public String ListaVentasCliente(Model model, HttpSession session){

        Usuarios sede = (Usuarios) session.getAttribute("usuario");
        model.addAttribute("listaProductos", ventasRepository.obtenerDatosPorProducto());
        return "sede/ventasPorProducto";
    }


}
