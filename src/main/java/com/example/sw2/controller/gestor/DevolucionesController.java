package com.example.sw2.controller.gestor;

import com.example.sw2.entity.*;
import com.example.sw2.repository.AsignadosSedesRepository;
import com.example.sw2.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/gestor/devoluciones")
public class DevolucionesController {

    @Autowired
    AsignadosSedesRepository asignadosSedesRepository;
    @Autowired
    UsuariosRepository usuariosRepository;

    @GetMapping(value = {"", "/"})
    public String ListaDevoluciones(@ModelAttribute("sede") Usuarios u,
                                    @RequestParam(value = "id", required = false) Integer dni,
                                    Model model, Authentication auth){

        Usuarios gestor = usuariosRepository.findByCorreo(auth.getName());
        model.addAttribute("devueltos", asignadosSedesRepository.DatosDevolucion(4, gestor.getIdusuarios()));
        return "gestor/devoluciones";
    }

    @GetMapping("/info")
    public String InfoSede(@ModelAttribute("sede") Usuarios sede,
                            @RequestParam(value = "id") int dni,
                           Model model){
        sede = usuariosRepository.findByIdusuarios(dni);
        System.out.println(sede.getNombre());
        model.addAttribute("sede1", sede);
        return "redirect:/gestor/devoluciones";
    }

    @PostMapping("/confirmar")
    public String Confirmar(){
        return "redirect:/devoluciones/";
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Usuarios>> getTienda(@RequestParam(value = "id") int id){
        return new ResponseEntity<>(usuariosRepository.findById(id), HttpStatus.OK);
    }
}
