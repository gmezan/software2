package com.example.sw2.controller.gestor;

import com.example.sw2.entity.Categorias;
import com.example.sw2.repository.CategoriasRepository;
import com.example.sw2.utils.UploadObject;
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
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/gestor/categoria")
public class CategoriaController {

    @Autowired
    CategoriasRepository categoriasRepository;


    @GetMapping(value = {"", "/"})
    public String listCat(@ModelAttribute("categoria_modal") Categorias cat, Model model) {
        model.addAttribute("lista", categoriasRepository.findAll());
        return "gestor/categorias";
    }


    @GetMapping("/new")
    public String newCat(@ModelAttribute("categoria") Categorias cat) {
            return "gestor/categoriasForm";
    }

    @GetMapping("/edit")
    public String formCat(@ModelAttribute("categoria") Categorias cat, @RequestParam(value = "codigo") String id,
                          Model model) {
        Optional<Categorias> c = categoriasRepository.findById(id);
        if (c.isPresent()) {
            cat =c.get();
            model.addAttribute("categoria",cat);
            return "gestor/categoriasForm";
        }
        return "redirect:/gestor/categoria";
    }


    @PostMapping("/save")
    public String editCat(@ModelAttribute("categoria") @Valid Categorias categorias,
                          BindingResult bindingResult, RedirectAttributes attr, Model model) {
        if(bindingResult.hasErrors()){
            return "gestor/categoriasForm";
        }
        else {
            Optional<Categorias> optionalCategorias = categoriasRepository.findById(categorias.getCodigo());
            if (optionalCategorias.isPresent()) {
                Categorias cat = optionalCategorias.get();
                System.out.println(cat.getCodigo());
                categorias.setFechamodificacion(LocalDateTime.now());
                categorias.setFechacreacion(cat.getFechacreacion());
                attr.addFlashAttribute("msg", "Producto actualizado exitosamente");
            }
            else {
                categorias.setFechacreacion(LocalDateTime.now());
                attr.addFlashAttribute("msg", "Producto creado exitosamente");
            }
            categoriasRepository.save(categorias);
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

    @ResponseBody
    @GetMapping(value = "/uploadFile",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadFile(){
        try {
            UploadObject.main();
            return new ResponseEntity<>("nice", HttpStatus.OK);
        }
        catch (IOException ex){
            return new ResponseEntity<>(ex.toString(), HttpStatus.ACCEPTED);
        }
    }




}
