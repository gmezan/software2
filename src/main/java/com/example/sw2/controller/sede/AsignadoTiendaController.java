package com.example.sw2.controller.sede;

import com.example.sw2.entity.Tienda;
import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.AsignacionTiendasRepository;
import com.example.sw2.repository.UsuariosRepository;
import com.example.sw2.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("sede/AsignadoTienda")
public class AsignadoTiendaController {

    @Autowired
    AsignacionTiendasRepository asignacionTiendasRepository;

    @Autowired
    VentasRepository ventasRepository;


    @GetMapping(value = {"", "/"})
    public String ListaAsignacionTiendas(@ModelAttribute("venta") Ventas venta, Model model){

        model.addAttribute("listaDatosAsignados", asignacionTiendasRepository.obtenerDatosAsignados());
        return "sede/asignadoTiendas";
    }

    @PostMapping("/registrar")
    public String RegistrarVentas(@ModelAttribute("venta") Ventas venta,
                                  Model model, RedirectAttributes attr){

        Optional<Ventas> optionalVenta = ventasRepository.findById(venta.getId());

        attr.addFlashAttribute("msg", "Venta registrada exitosamente");
        ventasRepository.save(venta);
        return "redirect:/sede/tienda";
    }




}
