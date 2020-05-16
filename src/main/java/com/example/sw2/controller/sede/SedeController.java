package com.example.sw2.controller.sede;

import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.AsignadosSedesRepository;
import com.example.sw2.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/sede")
public class SedeController {

    @Autowired
    AsignadosSedesRepository asignadosSedesRepository;


    @GetMapping(value = {"/",""}) public String init(){
        return "redirect:/sede/tienda";}


    @GetMapping("productosConfirmados")
    public String productosConfirmados(HttpSession session, Model model){

        Usuarios sede = (Usuarios) session.getAttribute("usuario");

        model.addAttribute("listaAsignadosSedes",asignadosSedesRepository.findBySede(sede));
        return "sede/ListaProductosConfirmados";

    }
}
    