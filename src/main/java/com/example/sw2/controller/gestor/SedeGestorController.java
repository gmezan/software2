package com.example.sw2.controller.gestor;

import com.example.sw2.entity.Roles;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.RolesRepository;
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
import java.util.List;
import java.util.Optional;


@Controller
//@RequestMapping("/gestor/sede")
public class SedeGestorController {/* :(


    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    RolesRepository rolesRepository;

    @GetMapping(value = {""})
    public String listar(@ModelAttribute("sede") Usuarios sede, Model model) {

        model.addAttribute("listaSedes", usuariosRepository.buscarSedes());
        model.addAttribute("listaRoles", rolesRepository.findAll());
        return "gestor/sedes";
    }

    @GetMapping(value = {"/form"})
    public String form(@ModelAttribute("sede") Usuarios sede, Model model) {
        model.addAttribute("listRol", rolesRepository.findAll());
        return "gestor/sedesForm";
    }

    @PostMapping("/save")
    public String saveEdit(@ModelAttribute("sede") @Valid Usuarios sede,
                           BindingResult bindingResult, RedirectAttributes attr, Model model) {
        if (bindingResult.hasErrors()) {
            Optional<Usuarios> optionalSedes = usuariosRepository.findById(sede.getIdusuarios());
            if (optionalSedes.isPresent()) {
                model.addAttribute("listaSedes", usuariosRepository.buscarSedes());
                model.addAttribute("msg", "ERROR");
                return "gestor/sedes";
            } else {
                model.addAttribute("msg", "ERROR");
                return "gestor/sedesForm";
            }
        } else {
            Optional<Usuarios> optionalSedes = usuariosRepository.findById(sede.getIdusuarios());
            if (optionalSedes.isPresent()) {
                Usuarios sed = optionalSedes.get();
                //System.out.println(cat.getCodigo());
                sede.setFechamodificacion(LocalDateTime.now());
                sede.setFechacreacion(sed.getFechacreacion());
                sede.setPassword(sed.getPassword());
                sede.setCuentaactivada(sed.getCuentaactivada());
                sede.setRoles(sed.getRoles());
                attr.addFlashAttribute("msg", "Sede actualizada exitosamente");
            } else {
                sede.setFechacreacion(LocalDateTime.now());
                List<Roles> listaRol = rolesRepository.findAll();
                Roles idrol = listaRol.get(2);
                sede.setRoles(idrol);
                attr.addFlashAttribute("msg", "Sede creada exitosamente");
            }
            usuariosRepository.save(sede);
            return "redirect:/gestor/sede";
        }

    }

    @GetMapping("/delete")
    public String deleteCat(Model model,
                            @RequestParam("codigo") int id,
                            RedirectAttributes attr) {
        Optional<Usuarios> s = usuariosRepository.findById(id);

        if (s.isPresent()) {
            usuariosRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Sede borrada exitosamente");
        }
        return "redirect:/gestor/sede";
    }

    @ResponseBody
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Usuarios>> getCat(@RequestParam(value = "id") int id) {

        return new ResponseEntity<>(usuariosRepository.findById(id), HttpStatus.OK);
    }


*/


}
