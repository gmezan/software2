package com.example.sw2.controller;


import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = {"/",""})
public class LoginController {

    @Autowired
    UsuariosRepository usuariosRepository;


    @GetMapping(value = {"/","/loginForm"})
    public String login(){

        return "login";
    }

    @GetMapping("/redirectByRole")
    public String redirectByRole(Authentication auth, HttpSession session) {
        String rol = "";
        for (GrantedAuthority role : auth.getAuthorities()) {
            rol = role.getAuthority();
            break;
        }

        Usuarios usuarioLogueado = usuariosRepository.findByCorreo(auth.getName());
        session.setAttribute("usuario", usuarioLogueado);

        switch (rol) {
            case "admin":
                return "redirect:/admin/";
            case "gestor":
                return "redirect:/gestor/";
            case "sede":
                return "redirect:/sede/";
        }
        return "/";

    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(){ return "forgot-password";}

    @GetMapping("/processForgotPassword")
    public String processForgotPassword(Model model, @RequestParam(value = "username", required = true) String email){
        System.out.println(email);
        model.addAttribute("msg", email);
        return "message-sent";
    }



}
