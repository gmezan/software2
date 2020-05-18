package com.example.sw2.controller.gestor;

import com.example.sw2.entity.Artesanos;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.ArtesanosRepository;
import com.example.sw2.repository.ComunidadesRepository;
import com.example.sw2.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gestor/artesano")
public class ArtesanoController {

    @Autowired
    ArtesanosRepository artesanosRepository;

    @Autowired
    ComunidadesRepository comunidadesRepository;

    @Autowired
    UsuariosRepository usuariosRepository;

    @GetMapping(value = {""})
    public String listCat(@ModelAttribute("artesano") Artesanos artesanos, Model model) {
        model.addAttribute("lista", artesanosRepository.findAll());
        model.addAttribute("comunidades", comunidadesRepository.findAll());
        return "gestor/artesanos";
    }

    @PostMapping("/save")
    public String editCat(@ModelAttribute("artesano") @Valid Artesanos artesanos,
                          BindingResult bindingResult, @RequestParam("type") int type,
                          RedirectAttributes attr, Model model) {
        if(type==1 && artesanosRepository.findById(artesanos.getCodigo()).isPresent()){ //if new
            bindingResult.rejectValue("codigo","error.user","Este codigo ya existe");
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("lista", artesanosRepository.findAll());
            model.addAttribute("comunidades", comunidadesRepository.findAll());
            model.addAttribute("msg", "ERROR");
            return "gestor/artesanos";
        }
        else {
            Optional<Artesanos> optionalArtesanos = artesanosRepository.findById(artesanos.getCodigo());
            if (optionalArtesanos.isPresent()) {
                Artesanos cat = optionalArtesanos.get();
                System.out.println(cat.getCodigo());
                artesanos.setFechamodificacion(LocalDateTime.now());
                artesanos.setFechacreacion(cat.getFechacreacion());
                attr.addFlashAttribute("msg", "Artesano actualizado exitosamente");
            }
            else {
                artesanos.setFechacreacion(LocalDateTime.now());
                attr.addFlashAttribute("msg", "Artesano creado exitosamente");
            }
            artesanosRepository.save(artesanos);
            return "redirect:/gestor/artesano";
        }

    }


    @GetMapping("/delete")
    public String deleteCat(Model model,
                            @RequestParam("codigo") String id,
                            RedirectAttributes attr) {
        Optional<Artesanos> c = artesanosRepository.findById(id);

        if (c.isPresent()) {
            artesanosRepository.deleteById(id);
            attr.addFlashAttribute("msg","Artesano borrado exitosamente");
        }
        return "redirect:/gestor/artesano";
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Artesanos>> getCat(@RequestParam(value = "id") String id){

        return new ResponseEntity<>(artesanosRepository.findById(id), HttpStatus.OK);

    }


}
