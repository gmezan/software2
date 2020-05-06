package com.example.sw2.controller.gestor;

import com.example.sw2.entity.Categorias;
import com.example.sw2.repository.CategoriasRepository;
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
@RequestMapping("/gestor/categoria")
public class CategoriaController {

    @Autowired
    CategoriasRepository categoriasRepository;


    @GetMapping(value = {"", "/"})
    public String listCat(@ModelAttribute("categoria") Categorias cat, Model model) {
        model.addAttribute("lista", categoriasRepository.findAll());
        return "gestor/categorias";
    }

    /*
    @GetMapping("/new")
    public String newCat(Model model) {
        return "gestor/newFrm";
    }*/

    @PostMapping("/save")
    public String saveCat(@ModelAttribute("categoria") @Valid Categorias categorias,
                          BindingResult bindingResult, RedirectAttributes attr, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("lista", categoriasRepository.findAll());
            return "gestor/categorias";
        }
        else {
            Optional<Categorias> c = categoriasRepository.findById(categorias.getCodigo());
            if (c.isPresent()) {
                categorias.setFechamodificacion(LocalDateTime.now());
                categorias.setFechacreacion(c.get().getFechacreacion());
                attr.addFlashAttribute("msg", "Producto actualizado exitosamente");
            } else {
                categorias.setFechacreacion(LocalDateTime.now());
                attr.addFlashAttribute("msg", "Producto creado exitosamente");
            }
            categoriasRepository.save(categorias);
            return "redirect:/gestor/categoria";
        }

    }

    @GetMapping("/edit")
    public String editCat(Model model,@RequestParam("id") String id) {

        Optional<Categorias> optProduct = categoriasRepository.findById(id);

        if (optProduct.isPresent()) {
            Categorias product = optProduct.get();
            model.addAttribute("product", product);
            return "product/editFrm";
        } else {
            return "redirect:/gestor/categoria";
        }
    }

    @GetMapping("/delete")
    public String deleteCat(Model model,
                                      @RequestParam("codigo") String id,
                                      RedirectAttributes attr) {

        Optional<Categorias> c = categoriasRepository.findById(id);

        if (c.isPresent()) {
            categoriasRepository.deleteById(id);
            attr.addFlashAttribute("msg","Producto borrado exitosamente");
        }
        return "redirect:/gestor/categoria";

    }



    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Categorias>> getCat(@RequestParam(value = "id") String id){
        return new ResponseEntity<>(categoriasRepository.findById(id), HttpStatus.OK);
    }





}
