package com.example.sw2.controller;

import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.NotificaRepository;
import com.example.sw2.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    NotificaRepository notificaRepository;

    @GetMapping("")
    public String  listNotf(Model model, HttpSession session){
        Usuarios u = (Usuarios) session.getAttribute("usuario");
        model.addAttribute("lista", notificaRepository.findNotificasByUsuarios_Idusuarios(u.getIdusuarios()));
        return "notification";
    }

    @ResponseBody
    @GetMapping(value = "number", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getNumber(HttpSession session){
        return new ResponseEntity<>(
                notificaRepository.getNotificationNumber(((Usuarios)session.getAttribute("usuario")).getIdusuarios()).getValue(),
                HttpStatus.OK
        );
    }


}
