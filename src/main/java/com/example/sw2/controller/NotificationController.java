package com.example.sw2.controller;

import com.example.sw2.entity.Notifica;
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
import java.util.List;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    NotificaRepository notificaRepository;

    private final int LIMIT_SHORT_LIST = 3;

    @ResponseBody
    @GetMapping("/list")
    public ResponseEntity<List<Notifica>> listNotf(Model model, HttpSession session){
        List<Notifica> list =
        notificaRepository.findNotificasByUsuario_Idusuarios(((Usuarios)session.getAttribute("usuario")).getIdusuarios());
        list.forEach(Notifica::createBeautifiedDatetime);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/shortList")
    public ResponseEntity<List<Notifica>> shortListNotf(Model model, HttpSession session){
        List<Notifica> list =
        notificaRepository.shortList(((Usuarios)session.getAttribute("usuario")).getIdusuarios(), LIMIT_SHORT_LIST);
        list.forEach(Notifica::createBeautifiedDatetime);
        return new ResponseEntity<>(list, HttpStatus.OK);
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
