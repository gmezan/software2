package com.example.sw2.controller.sede;

import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Tienda;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.AsignadosSedesRepository;
import com.example.sw2.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/sede")
public class SedeController {

    @Autowired
    AsignadosSedesRepository asignadosSedesRepository;


    @GetMapping(value = {"/",""}) public String init(){
        return "redirect:/sede/tienda";}


    @GetMapping("productosPorConfirmar")
    public String productosPorConfirmar(@ModelAttribute("productosPorConfirmar") AsignadosSedes productosPorConfirmar, HttpSession session, Model model){

        Usuarios sede = (Usuarios) session.getAttribute("usuario");

        model.addAttribute("listaProductosPorConfirmar",asignadosSedesRepository.findBySede(sede));
        return "sede/ListaProductosPorConfirmar";

    }

    @GetMapping("productosConfirmados")
    public String productosConfirmados(){
        return "";
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<AsignadosSedes>> getAsignadosSede(@RequestParam(value = "id") int id){
        return new ResponseEntity<>(asignadosSedesRepository.findById(id), HttpStatus.OK);
    }


}
    