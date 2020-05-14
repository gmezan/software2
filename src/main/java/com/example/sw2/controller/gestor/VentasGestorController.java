package com.example.sw2.controller.gestor;

import com.example.sw2.entity.Comunidades;
import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.VentasRepository;
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
@RequestMapping("/gestor/venta")
public class VentasGestorController {/*

    @Autowired
    VentasRepository ventasRepository;


    @GetMapping(value = {"", "/"})
    public String listVen(@ModelAttribute("venta") Ventas ven, Model model) {
        model.addAttribute("lista", ventasRepository.obtenerDatosGestorVentas());
        return "gestor/ventas";
    }

    @PostMapping("/save")
    public String editVen(@ModelAttribute("venta") @Valid Ventas ventas,
                          BindingResult bindingResult, RedirectAttributes attr, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("lista", ventasRepository.findAll());
            model.addAttribute("msg", "ERROR");
            return "gestor/ventas";
        }
        else {
            Optional<Ventas> optionalVentas = ventasRepository.findById(ventas.getNumerodocumento());
            if (optionalVentas.isPresent()) {
                Ventas ven = optionalVentas.get();
                System.out.println(ven.getNumerodocumento());
                ventas.setFechamodificacion(LocalDateTime.now());
                ventas.setFechacreacion(ven.getFechacreacion());
                attr.addFlashAttribute("msg", "Venta actualizada exitosamente");
            }
            ventasRepository.save(ventas);
            return "redirect:/gestor/venta";
        }
    }

    @GetMapping("/delete")
    public String deleteCom(Model model,
                            @RequestParam("numerodocumento") String id,
                            RedirectAttributes attr) {
        Optional<Ventas> c = ventasRepository.findById(id);
        if (c.isPresent()) {
            ventasRepository.deleteById(id);
            attr.addFlashAttribute("msg","Venta borrada exitosamente");
        }
        return "redirect:/gestor/comunidad";
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Ventas>> getVen(@RequestParam(value = "id") String id){
        return new ResponseEntity<>(ventasRepository.findById(id), HttpStatus.OK);
    }*/
}
