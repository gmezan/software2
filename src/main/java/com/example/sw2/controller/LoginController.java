package com.example.sw2.controller;


import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.UsuariosRepository;
import com.example.sw2.utils.CustomMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/",""})
public class LoginController {

    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    CustomMailService customMailService;


    @GetMapping(value = {"/","/loginForm"})
    public String login(Authentication auth){
        String rol = "";

        if(auth == null){
            return "login";
        }else{
            for (GrantedAuthority role : auth.getAuthorities()) {
                rol = role.getAuthority();
                break;
            }
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
    public String forgotPassword(@ModelAttribute("usuario") Usuarios usuarios){return "forgot-password";}

    @PostMapping("/processForgotPassword")
    public String processForgotPassword(@ModelAttribute("usuario") Usuarios usuarios,
                                        BindingResult bindingResult, Model model,
                                        RedirectAttributes attr) throws IOException, MessagingException, NoSuchAlgorithmException {


        String email = "";
        String id = "";
        String token = "";


        //valida si el campo está vacío
        if(usuarios.getCorreo() == null){
            bindingResult.rejectValue("correo", "error.user", "Este campo no puede estar vacío");
        }else{
            //valida si es una dirección tiene un dominio váldio
            if (!(usuarios.getCorreo().contains("@"))){
                bindingResult.rejectValue("correo", "error.user", "Ingrese una dirección de correo válida");
            }else{
                //Busca el correo en la bd
                Usuarios u = usuariosRepository.findByCorreo(usuarios.getCorreo());
                if(u == null){
                    bindingResult.rejectValue("correo","error.user","Este email no está registrado");
                }else{
                    id = Integer.toString(u.getIdusuarios()) + Integer.toString(getRandom());
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] encodedhash = digest.digest(
                            id.getBytes(StandardCharsets.UTF_8));
                    token =bytesToHex(encodedhash);
                    u.setToken(token);
                    System.out.println(bytesToHex(encodedhash));
                    usuariosRepository.save(u);


                    email = u.getCorreo();
                    System.out.println(email);
                }
            }
        }

        if(bindingResult.hasErrors()){
            return "forgot-password";
        }else{
            customMailService.sendEmail(email,
                    "Recuperación de contraseña", "Nueva contraseña",
                    "Para restablecer su contraseña ingrese al siguiente link \n"
                            + "http://localhost:8080/nuevacontraseña?t="+ token);
            return "message-sent";
        }

    }

    @GetMapping("/nuevacontraseña")
    public String NuevaContraseña(@ModelAttribute("usuario") Usuarios u,@RequestParam("t") String token,
                                  Model model){

        u = usuariosRepository.findByToken(token);
        model.addAttribute("token", token);
        return "new-password";
    }

    @PostMapping("/guardarcontraseña")
    public String GuardarNuevaContraseña(@ModelAttribute("usuario") Usuarios u,
                                         BindingResult bindingResult,Model model,
                                         @RequestParam("pass") String pass,@RequestParam("token") String token){

        //System.out.println(pass);
        //System.out.println(u.getPassword2());
        Usuarios usuario = usuariosRepository.findByToken(token);

        //System.out.println(usuario.getCorreo());

        if(pass == null){
            bindingResult.rejectValue("password2","error.user","Este campo no puede estar vacío");
        }else{
            if(!(u.getPassword().equals(pass))){
                bindingResult.rejectValue("password2","error.user","Las contraseñas deben de ser idénticas");
            }else{
                if(!u.validatePassword()){
                    bindingResult.rejectValue("password2","error.user","La contraseña debe tener 8 caracteres como mínimo y un caracter especial");
                }
            }
        }


        if(bindingResult.hasErrors()){
            model.addAttribute("msgError", "ERROR");
            model.addAttribute("token", token);
            u.setPassword2("");
            u.setPassword("");
            return "new-password";
        }else{
            //System.out.println(usuario.getCorreo());
            u.setPassword(pass);
            usuariosRepository.actualizar_password(token, u.getPassword(), usuario.getIdusuarios());
            return "redirect:/";
        }

    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static int getRandom(){
        int min = 10;
        int max = 1000;
        int x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }



}
