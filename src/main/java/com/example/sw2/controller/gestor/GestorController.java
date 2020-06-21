package com.example.sw2.controller.gestor;

import com.example.sw2.constantes.VentasId;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/gestor")
public class GestorController {

    //private final int ID_USU = ;

       @Autowired
       UsuariosRepository usuariosRepository;

    @GetMapping(value = {"/", ""})
    public String init() {
        return "redirect:/gestor/inventario";
    }


    @GetMapping("/perfil")
    public String perfilGestor(@ModelAttribute("gestor") Usuarios usuarios,
                               Model model) {
        model.addAttribute("lista", usuariosRepository.findAll());
        return "gestor/perfilGestor";
    }

    @PostMapping("/save")
    public String editCom(@ModelAttribute("gestor") @Valid Usuarios usuarios,
                          BindingResult bindingResult,
                          RedirectAttributes attr, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("lista", usuariosRepository.findAll());
            model.addAttribute("msgError", "ERROR");
            return "gestor/perfilGestor";
        } else {
            Optional<Usuarios> optionalUsuarios = usuariosRepository.findById(usuarios.getIdusuarios());
            if (optionalUsuarios.isPresent()) {
                Usuarios usu = optionalUsuarios.get();
                usuarios.setFoto(usu.getFoto());
                usuarios.setPassword(usu.getPassword());
                usuarios.setCuentaactivada(usu.getCuentaactivada());
                usuarios.setRoles(usu.getRoles());
                attr.addFlashAttribute("msg", "Su perfil se actualizó exitosamente");
            }
            usuariosRepository.save(usuarios);
            return "redirect:/gestor/perfil";
        }
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Usuarios>> getUsu(@RequestParam(value = "id") int id){
        return new ResponseEntity<>(usuariosRepository.findById(id), HttpStatus.OK);
    }
}
