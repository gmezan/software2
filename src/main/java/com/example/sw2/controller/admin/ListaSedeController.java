package com.example.sw2.controller.admin;

import com.example.sw2.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/sede")
public class ListaSedeController {

    @Autowired
    UsuariosRepository usuariosRepository;

    @GetMapping(value = {""})
    public String listaSede(Model model){
        model.addAttribute("lista", usuariosRepository.findUsuariosByRoles_Nombrerol("sede"));
        return "admin/listaSede";
    }

}
