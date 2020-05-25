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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public String ventasDeProductos(Model model, @RequestParam("id") String id){
        Optional<Inventario> optionalInventario = inventarioRepository.findById(id);
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
                                 Ventas ventas,
                                 HttpSession session){

        Usuarios usuarios = (Usuarios) session.getAttribute("usuario");
        ventas.setId(new VentasId(tipodocumento,documento));
        ventas.setVendedor(usuarios);
        ventas.setFechacreacion(LocalDateTime.now());
        ventasRepository.save(ventas);

        //System.out.println(ventas.getVendedor().getCorreo());
        //System.out.println(ventas.getInventario().getCodigoinventario());
        //System.out.println(ventas.getRucdni());
        //System.out.println(ventas.getNombrecliente());
        //System.out.println(tipodocumento);
        //System.out.println(documento);
        //System.out.println(ventas.getPrecioventa());
        //System.out.println(ventas.getLugarventa());
        //System.out.println(ventas.getCantidad());
        //System.out.println(ventas.getFecha());
        //System.out.println(ventas.getId().getTipodocumento());
        //System.out.println(ventas.getId().getNumerodocumento());
        //System.out.println(ventas.getFechacreacion());

        return "redirect:/gestor/productosDisponibles";
    }


    @GetMapping(value="asignar")
    public String asignarProducto(Model model, @RequestParam("id") String id){
        Optional<Inventario> optionalInventario = inventarioRepository.findById(id);
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
                                              AsignadosSedes asignadosSedes,
                                              HttpSession session){
        LocalDate day = LocalDate.parse(fecha);
        Usuarios usuarios = (Usuarios) session.getAttribute("usuario");
        Optional<Usuarios> optionalUsuarios = usuariosRepository.findById(sede);
        Usuarios sedes = optionalUsuarios.get();
        Optional<Inventario> optionalInventario = inventarioRepository.findById(inventario);
        Inventario inv = optionalInventario.get();
        asignadosSedes.setId(new AsignadosSedesId(usuarios,sedes,inv,day));
        asignadosSedes.setCantidadactual(asignadosSedes.getStock());
        asignadosSedes.setCodEstadoAsignacion(1);
        asignadosSedes.setFechacreacion(LocalDateTime.now());

        asignadosSedesRepository.save(asignadosSedes);

        System.out.println(asignadosSedes.getId().getGestor().getCorreo());
        System.out.println(asignadosSedes.getId().getFechaenvio());
        System.out.println(asignadosSedes.getStock());
        System.out.println(asignadosSedes.getPrecioventa());


        return "redirect:/gestor/productosDisponibles";
    }


}
