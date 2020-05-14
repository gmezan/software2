package com.example.sw2.controller.gestor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gestor")
public class GestorController {
    @GetMapping(value = {"/",""}) public String init(){
        return "redirect:/gestor/comunidad";}
}
