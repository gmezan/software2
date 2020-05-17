package com.example.sw2.controller.gestor;

import com.example.sw2.constantes.VentasId;
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
public class VentasGestorController {

    @Autowired
    VentasRepository ventasRepository;


    @GetMapping(value = {"", "/"})
    public String listVen(@ModelAttribute("venta") Ventas ven, Model model) {
        model.addAttribute("lista", ventasRepository.findAll());
        return "gestor/ventas";
    }

    @PostMapping("/save")
    public String editVen(@ModelAttribute("venta") @Valid Ventas ventas,
                          BindingResult bindingResult, RedirectAttributes attr, Model model,
                          @RequestParam("id1") String id1,
                          @RequestParam("id2") int id2) {
        if(bindingResult.hasErrors()){
            model.addAttribute("lista", ventasRepository.findAll());
            model.addAttribute("msg", "ERROR");
            return "gestor/ventas";
        }
        else {
            Optional<Ventas> optionalVentas = ventasRepository.findById(new VentasId(id2,id1));
            if (optionalVentas.isPresent()) {
                Ventas ven = optionalVentas.get();
                System.out.println(new VentasId(id2,id1));
                ventas.setFechamodificacion(LocalDateTime.now());
                ventas.setFechacreacion(ven.getFechacreacion());
                attr.addFlashAttribute("msg", "Venta actualizada exitosamente");
            }
            ventasRepository.save(ventas);
            return "redirect:/gestor/venta";
        }
    }

    @GetMapping("/delete")
    public String deleteVen(@RequestParam("id1") String id1,
                            @RequestParam("id2") int id2,
                            RedirectAttributes attr) {
        Optional<Ventas> c = ventasRepository.findById(new VentasId(id2,id1));
        if (c.isPresent()) {
            ventasRepository.deleteById(new VentasId(id2,id1));
            attr.addFlashAttribute("msg","Venta borrada exitosamente");
        }
        return "redirect:/gestor/venta";
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Ventas>> getVen(@RequestParam(value = "id1") String id1, @RequestParam(value = "id2")int id2){
        return new ResponseEntity<>(ventasRepository.findById(new VentasId(id2,id1)), HttpStatus.OK);
    }
}