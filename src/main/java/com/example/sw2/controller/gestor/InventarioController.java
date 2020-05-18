package com.example.sw2.controller.gestor;

import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.entity.Artesanos;
import com.example.sw2.entity.Inventario;
import com.example.sw2.entity.Productos;
import com.example.sw2.repository.*;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gestor/inventario")
public class InventarioController {
    @Autowired
    InventarioRepository inventarioRepository;
    @Autowired
    CategoriasRepository categoriasRepository;

    @Autowired
    ComunidadesRepository comunidadesRepository;

    @Autowired
    ProductosRepository productosRepository;

    @Autowired
    ArtesanosRepository artesanosRepository;


    @GetMapping(value = {""})
    public String listInv(@ModelAttribute("inventario") Inventario inventario, Model m) {
        m.addAttribute("listaInv", inventarioRepository.findAll());

        return "gestor/inventarioGestor";
    }

    @GetMapping(value = {"/form"})
    public String form(@ModelAttribute("inventario") Inventario inventario, Model m) {

        m.addAttribute("Meses", CustomConstants.getMeses());
        m.addAttribute("taman", CustomConstants.getTamanhos());
        m.addAttribute("tipoAdqui", CustomConstants.getTiposAdquisicion());
        m.addAttribute("lineas", CustomConstants.getLineas());
        m.addAttribute("listCat", categoriasRepository.findAll());
        m.addAttribute("listCom", comunidadesRepository.findAll());
        return "gestor/inventarioGestorForm";
    }

    @GetMapping(value = {"/save"})
    public String save(@ModelAttribute("inventario") @Valid Inventario inventario,
                       BindingResult bindingResult, Model m, RedirectAttributes attributes) {

        if(bindingResult.hasErrors()){
            m.addAttribute("Meses", CustomConstants.getMeses());
            m.addAttribute("taman", CustomConstants.getTamanhos());
            m.addAttribute("tipoAdqui", CustomConstants.getTiposAdquisicion());
            m.addAttribute("lineas", CustomConstants.getLineas());
            m.addAttribute("listCat", categoriasRepository.findAll());
            m.addAttribute("listCom", comunidadesRepository.findAll());
            return "gestor/inventarioGestorForm";
        }
        else {


            return "redirect:/gestor/inventario";
        }
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/form/getLinea",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Productos>> getCom(@RequestParam(value = "linea") String linea){
        return new ResponseEntity<>(productosRepository.findProductosByCodigolinea(linea), HttpStatus.OK);
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/form/getArtesanos",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artesanos>> getArtesanos(@RequestParam(value = "comunidad") String comunidad){
        return new ResponseEntity<>(artesanosRepository.findArtesanosByComunidades_Codigo(comunidad), HttpStatus.OK);
    }



}
