package com.example.sw2.controller.gestor;

import com.example.sw2.constantes.AsignadosSedesId;
import com.example.sw2.constantes.VentasId;
import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Inventario;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.AsignadosSedesRepository;
import com.example.sw2.repository.InventarioRepository;
import com.example.sw2.repository.UsuariosRepository;
import com.example.sw2.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/gestor/productosDisponibles")

public class ProductosDisponiblesController {

    @Autowired
    InventarioRepository inventarioRepository;
    @Autowired
    VentasRepository ventasRepository;
    @Autowired
    AsignadosSedesRepository asignadosSedesRepository;
    @Autowired
    UsuariosRepository usuariosRepository;

    @GetMapping(value ="")
    public String listarProductosDisponibles(Model model){
        model.addAttribute("listainventario",inventarioRepository.findAll());
        return "gestor/productosDisponibles";
    }


    @GetMapping(value="venta")
    public String ventasDeProductos(@ModelAttribute("ventas") Ventas ventas, Model model, @RequestParam("x") String x){
        Optional<Inventario> optionalInventario = inventarioRepository.findById(x);
        if ( optionalInventario.isPresent()){
            Inventario inventario=optionalInventario.get();
            model.addAttribute("inv", inventario);
            return "gestor/productosDisponiblesForm";
        }
        return "redirect:/gestor/productosDisponibles";
    }

    @PostMapping("/registrarventa")
    public String registrarventa(@RequestParam("tipodocumento") int tipodocumento,
                                 @RequestParam("documento") String documento,
                                 @ModelAttribute("ventas") @Valid Ventas ventas, BindingResult bindingResult,
                                 Model model, RedirectAttributes attributes,
                                 HttpSession session){
        if (bindingResult.hasErrors()) {
            model.addAttribute("inv", ventas.getInventario());
            return "gestor/productosDisponiblesForm";

        } else {
            Usuarios usuarios = (Usuarios) session.getAttribute("usuario");
            ventas.setId(new VentasId(tipodocumento, documento));
            ventas.setVendedor(usuarios);
            ventas.setFechacreacion(LocalDateTime.now());
            ventasRepository.save(ventas);
            attributes.addFlashAttribute("msg", "Venta de producto realizada");
            return "redirect:/gestor/productosDisponibles";
        }
    }


    @GetMapping(value="asignar")
    public String asignarProducto(@ModelAttribute("asignadosSedes") AsignadosSedes asignadosSedes ,Model model, @RequestParam("x") String x){
        Optional<Inventario> optionalInventario = inventarioRepository.findById(x);
        if ( optionalInventario.isPresent()){
            Inventario inventario=optionalInventario.get();
            model.addAttribute("inv", inventario);
            model.addAttribute("listasedes",usuariosRepository.findUsuariosByRoles_idroles(3));
            return "gestor/productosDisponiblesAsignar";
        }
        return "redirect:/gestor/productosDisponibles";
    }



    @PostMapping("/resgistrarasignacion")
    public String registrarAsignacionProducto(@RequestParam("sede") int sede,
                                              @RequestParam("fecha") String fecha,
                                              @RequestParam("inventario") String inventario,
                                              @ModelAttribute("asignadosSedes") @Valid AsignadosSedes asignadosSedes, BindingResult bindingResult,
                                              Model model, RedirectAttributes attributes,
                                              HttpSession session) {
        if (bindingResult.hasErrors()) {
            Optional<Inventario> optionalInventario = inventarioRepository.findById(inventario);
            Inventario inv = optionalInventario.get();
            model.addAttribute("inv", inv);
            model.addAttribute("listasedes",usuariosRepository.findUsuariosByRoles_idroles(3));
            return "gestor/productosDisponiblesAsignar";

        } else {

            LocalDate day = LocalDate.parse(fecha);
            Usuarios usuarios = (Usuarios) session.getAttribute("usuario");
            Optional<Usuarios> optionalUsuarios = usuariosRepository.findById(sede);
            Usuarios sedes = optionalUsuarios.get();
            Optional<Inventario> optionalInventario = inventarioRepository.findById(inventario);
            Inventario inv = optionalInventario.get();
            //asignadosSedes.setId(new AsignadosSedesId(usuarios, sedes, inv, day));
            asignadosSedes.setCantidadactual(asignadosSedes.getStock());
            //asignadosSedes.setCodEstadoAsignacion(1);
            asignadosSedes.setFechacreacion(LocalDateTime.now());

            asignadosSedesRepository.save(asignadosSedes);

            attributes.addFlashAttribute("msg", "Producto asignado exitosamente");
            return "redirect:/gestor/productosDisponibles";
        }
    }


}
