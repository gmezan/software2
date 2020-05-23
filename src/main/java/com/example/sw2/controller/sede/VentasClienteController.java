package com.example.sw2.controller.sede;

import com.example.sw2.constantes.VentasId;
import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sede/ventasCliente")
public class VentasClienteController {

    @Autowired
    VentasRepository ventasRepository;

    @GetMapping(value = {"", "/"})
    public String ListVentasCliente(@ModelAttribute("ventas") Ventas ventas,
                                    Model model){

        model.addAttribute("listaVentas", ventasRepository.findAll());
        return "sede/ventasPorCliente";
    }

    @GetMapping("/borrar")
    public String borrarVenta(@RequestParam("id1") String id1,
                              @RequestParam("id2") int id2,
                              RedirectAttributes attr) {


        Optional<Ventas> optProduct = ventasRepository.findById(new VentasId(id2,id1));

        if (optProduct.isPresent()) {
            ventasRepository.deleteById(new VentasId(id2, id1));
            attr.addFlashAttribute("msg","Venta eliminada exitosamente");
        }
        return "redirect:/sede/ventasCliente";

    }


}
