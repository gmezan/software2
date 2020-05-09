package com.example.sw2.controller.sede;

import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.InventarioRepository;
import com.example.sw2.repository.TiendaRepository;
import com.example.sw2.repository.UsuariosRepository;
import com.example.sw2.repository.VentasRepository;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.awt.*;
import java.util.Optional;

@Controller
@RequestMapping("/sede/ventasCliente")
public class VentasClienteController {

    @Autowired
    VentasRepository ventasRepository;

    @GetMapping(value = {"", "ListaVentasCliente"})
    public String ListVentasCliente(Model model){

        model.addAttribute("listaVentas", ventasRepository.obtenerDatosProducto());
        return "sede/ListaOrdenes";
    }

    @GetMapping("/borrarVenta")
    public String borrarVenta(@RequestParam("id") String id,
                                      RedirectAttributes attr) {

        Optional<Ventas> optProduct = ventasRepository.findById(id);

        if (optProduct.isPresent()) {
            ventasRepository.deleteById(id);
            attr.addFlashAttribute("msg","Venta eliminada exitosamente");
        }
        return "redirect:/sede/ventasCliente";

    }


}
