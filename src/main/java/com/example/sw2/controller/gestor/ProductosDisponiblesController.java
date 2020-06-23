package com.example.sw2.controller.gestor;

import com.example.sw2.constantes.AsignadosSedesId;
import com.example.sw2.constantes.CustomConstants;
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
                                 HttpSession session) {

        boolean fechavalida = true;
        if(ventas.getFecha()!=null) {

            if (ventas.getFecha().isBefore(ventas.getInventario().getFechaadquisicion())){
                fechavalida=false;
            }
        }else{
            bindingResult.rejectValue("fecha", "error.user", "Ingrese una fecha");
        }

        if ((bindingResult.hasErrors()) || (documento=="") || (ventas.getCantidad() > ventas.getInventario().getCantidadgestor()) || (fechavalida==false) ) {
            model.addAttribute("inv", ventas.getInventario());
            if(documento == "") { model.addAttribute("msg", "Debe ingresar un número de documento"); }
            if(fechavalida==false){model.addAttribute("msg1", "La fecha debe ser igual o posterior a la  fecha de aquisision del producto");}
            if(ventas.getCantidad() > ventas.getInventario().getCantidadgestor()){model.addAttribute("msg2", "No hay suficientes productos en stock");}


            //PRUEBA
            System.out.println(ventas.getCantidad());
            System.out.println(ventas.getId().getTipodocumento());
            System.out.println(ventas.getId().getNumerodocumento());
            System.out.println(ventas.getFecha());
            System.out.println(ventas.getInventario().getCodigoinventario());
            System.out.println(ventas.getLugarventa());
            System.out.println(ventas.getNombrecliente());
            System.out.println(ventas.getPrecioventa());
            return "gestor/productosDisponiblesForm";
        }else {
            Inventario inventario = ventas.getInventario();
            inventario.setCantidadgestor( inventario.getCantidadgestor() - ventas.getCantidad());
            inventario.setCantidadtotal( inventario.getCantidadtotal() - ventas.getCantidad());
            inventarioRepository.save(inventario);
            Usuarios usuarios = (Usuarios) session.getAttribute("usuario");
            ventas.setId(new VentasId(tipodocumento, documento));
            ventas.setVendedor(usuarios);
            ventasRepository.save(ventas);
            attributes.addFlashAttribute("msg", "Venta de producto realizada");
            return "redirect:/gestor/productosDisponibles";
        }
    }


    @GetMapping(value="asignar")
    public String asignarProducto(@ModelAttribute("asignadosSedes") AsignadosSedes asignadosSedes ,Model model, @RequestParam("x") String x){
        Optional<Inventario> optionalInventario = inventarioRepository.findById(x);
        if ( optionalInventario.isPresent()){
            asignadosSedes = new AsignadosSedes(new AsignadosSedesId(null,null,optionalInventario.get(),null,null));
            //Inventario inventario=optionalInventario.get();
            model.addAttribute("asignadosSedes", asignadosSedes);
            model.addAttribute("listasedes",usuariosRepository.findUsuariosByRoles_IdrolesOrderByApellido(3));
            return "gestor/productosDisponiblesAsignar";
        }
        return "redirect:/gestor/productosDisponibles";
    }



    @PostMapping("/resgistrarasignacion")
    public String registrarAsignacionProducto(@ModelAttribute("asignadosSedes") @Valid AsignadosSedes asignadosSedes, BindingResult bindingResult,
                                              Model model, RedirectAttributes attributes,
                                              HttpSession session) {
        Optional<Inventario> optionalInventario = inventarioRepository.findInventarioByCodigoinventarioAndCantidadgestorIsGreaterThan(
                asignadosSedes.getId().getProductoinventario().getCodigoinventario(),0
        );

        Optional<Usuarios> optionalUsuarios = usuariosRepository.findUsuariosByRoles_idrolesAndIdusuarios(3,
                asignadosSedes.getId().getSede().getIdusuarios());

        // Se verifica que el producto de Inventario
        if(!optionalInventario.isPresent()){
            attributes.addFlashAttribute("msg", "Hubo un error");
            return "redirect:/gestor/productosDisponibles";
        } else
            asignadosSedes.getId().setProductoinventario(optionalInventario.get());

        //Se verifica la sede
        if(!optionalUsuarios.isPresent())
            bindingResult.rejectValue("id.sede","error.user","Escoja una sede");
        else
            asignadosSedes.getId().setSede(optionalUsuarios.get());

        //Se verifica la fecha de envio
        if ((asignadosSedes.getFechaenvio()!=null) && asignadosSedes.getFechaenvio().isBefore(asignadosSedes.getId().getProductoinventario().getFechaadquisicion()))
            bindingResult.rejectValue("fechaenvio","error.user","La fecha debe ser después del :"+asignadosSedes.getId().getProductoinventario().getFechaadquisicion().toString());

        //Se verfica el precio de venta
        if (!((asignadosSedes.getId().getPrecioventa()!=null) && asignadosSedes.getId().getPrecioventa()>0.0))
            bindingResult.rejectValue("id.precioventa","error.user","Ingrese un precio válido");

        //Se verifica la cantidad asignada
        if((asignadosSedes.getStock()!=null) && (optionalInventario.get().getCantidadgestor()<asignadosSedes.getStock()))
            bindingResult.rejectValue("stock","error.user","La cantidad asignada es mayor a la cantidad disponible");

        if (bindingResult.hasErrors()) {
            model.addAttribute("asignadosSedes", asignadosSedes);
            model.addAttribute("listasedes",usuariosRepository.findUsuariosByRoles_IdrolesOrderByApellido(3));
            return "gestor/productosDisponiblesAsignar";
        } else {
            asignadosSedes.getId().setGestor((Usuarios)session.getAttribute("usuario"));

            asignadosSedes.getId().getProductoinventario().subtractCantidad(asignadosSedes.getStock());
            inventarioRepository.save(asignadosSedes.getId().getProductoinventario());
            asignadosSedes.getId().setEstadoasignacion(CustomConstants.ESTADO_ENVIADO_A_SEDE);
            asignadosSedes.setCantidadactual(asignadosSedes.getStock());
            asignadosSedesRepository.save(asignadosSedes);

            /*
            inv.setCantidadgestor(inv.getCantidadgestor()-asignadosSedes.getStock());
            inventarioRepository.save(inv);
            Usuarios usuarios = (Usuarios)session.getAttribute("usuario") ;
            Optional<Usuarios> optionalUsuarios = usuariosRepository.findById(sede);
            Usuarios sedes = optionalUsuarios.get();
            asignadosSedes.setId(new AsignadosSedesId(usuarios, sedes, inv,1,precio));
            asignadosSedes.setCantidadactual(asignadosSedes.getStock());
            System.out.println( "La puta fecha sin asignar es " + asignadosSedes.getFechaenvio());
            asignadosSedesRepository.save(asignadosSedes);
*/
            attributes.addFlashAttribute("msg", "Producto asignado exitosamente");
            return "redirect:/gestor/productosDisponibles";
        }
    }


}
