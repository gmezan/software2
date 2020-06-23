package com.example.sw2.controller.sede;

import com.example.sw2.constantes.AsignadosSedesId;
import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.constantes.VentasId;
import com.example.sw2.dto.DatosAsignadosTiendaDto;
import com.example.sw2.entity.*;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("sede/AsignadoTienda")
public class AsignadoTiendaController {

    @Autowired
    AsignacionTiendasRepository asignacionTiendasRepository;
    @Autowired
    VentasRepository ventasRepository;
    @Autowired
    AsignadosSedesRepository asignadosSedesRepository;
    @Autowired
    InventarioRepository inventarioRepository;
    @Autowired
    UsuariosRepository usuariosRepository;


    @GetMapping(value = {"", "/"})
    public String ListaAsignacionTiendas(@ModelAttribute("ventas") Ventas v,
                                         HttpSession session,
                                         Model model){
        Usuarios sede = (Usuarios) session.getAttribute("usuario");
        model.addAttribute("asignados", asignacionTiendasRepository.findAsignacionTiendasByStockGreaterThanAndAsignadosSedes_Id_Sede(0,sede));
        model.addAttribute("tipodoc", CustomConstants.getTiposDocumento());
        return "sede/asignadoTiendas";
    }

    @PostMapping("/registrar")
    public String RegistrarVentas(@ModelAttribute("ventas") @Valid Ventas venta,
                                  BindingResult bindingResult, HttpSession session,
                                  @RequestParam("id1") int idAstiendas,
                                  Model model, RedirectAttributes attr){

        Optional<AsignacionTiendas> optaTienda = asignacionTiendasRepository.findById(idAstiendas);
        Optional<Ventas> optVentas = ventasRepository.findById(venta.getId());
        AsignacionTiendas aTienda = optaTienda.get();

        System.out.println(venta.getPrecioventa());
        System.out.println(venta.getNombrecliente());
        System.out.println(venta.getLugarventa());
        System.out.println(venta.getInventario().getCodigoinventario());
        System.out.println(venta.getFecha());
        System.out.println(venta.getId().getNumerodocumento());
        System.out.println(venta.getId().getTipodocumento());
        System.out.println(venta.getCantidad());
        System.out.println(venta.getRucdni());
        System.out.println(venta.getVendedor().getIdusuarios());

        if(optVentas.isPresent()){
            bindingResult.rejectValue("id.numerodocumento", "error.user", "El número de documento ya ha sido registrado");
        }
        if(venta.getCantidad() > aTienda.getStock()){
            bindingResult.rejectValue("cantidad", "error.user","La cantidad no puede ser mayor al stock de la tienda");
        }
        if(venta.getFecha() != null){
            if(venta.getFecha().isBefore(aTienda.getFechaasignacion())){
                bindingResult.rejectValue("fecha", "error.user","La fecha de venta no puede ser antes de la fecha de asignación");
            }
        }

        if(bindingResult.hasErrors()){
            Usuarios sede = (Usuarios) session.getAttribute("usuario");
            model.addAttribute("asignados", asignacionTiendasRepository.findAsignacionTiendasByStockGreaterThanAndAsignadosSedes_Id_Sede(0,sede));
            model.addAttribute("id1", idAstiendas);
            model.addAttribute("tipodoc", CustomConstants.getTiposDocumento());
            model.addAttribute("msgErrorRegistrar", "ERROR");
            return "sede/asignadoTiendas";


        }else{
            ventasRepository.save(venta);
            //actualizar stock(Asignados_sedes) cant_total(inventario)
            asignacionTiendasRepository.tienda_registra(aTienda.getAsignadosSedes().getId().getGestor().getIdusuarios(),
                    venta.getVendedor().getIdusuarios(), venta.getInventario().getCodigoinventario(),
                    2,venta.getPrecioventa(), venta.getCantidad(), idAstiendas);

            attr.addFlashAttribute("msg", "Venta registrada exitosamente");
            return "redirect:/sede/AsignadoTienda";
        }
    }

    @GetMapping("/devolucion")
    public String DevolTienda(@ModelAttribute("ventas") Ventas v,
                              @RequestParam("id2") int idAstiendas,
                              BindingResult bindingResult,
                              HttpSession session,
                              Model model, RedirectAttributes attr){


        Optional<AsignacionTiendas> optAt = asignacionTiendasRepository.findById(idAstiendas);
        AsignacionTiendas at = optAt.get();

        if(v.getCantDevol() > at.getStock()){
            bindingResult.rejectValue("cantDevol", "error.user","La cantidad no puede ser mayor al stock actual de la tienda");
        }
        if(v.getCantDevol() == 0){
            bindingResult.rejectValue("cantDevol", "error.user","La cantidad tiene que ser mayor a 0");
        }
        if(v.getFecha() == null){
            bindingResult.rejectValue("fecha", "error.user","Tiene que asignar una fecha");
        }else{
            if(v.getFecha().isBefore(at.getFechaasignacion())){
                bindingResult.rejectValue("fecha", "error.user","La fecha de devolución no puede ser antes de la fecha de asignación");
            }
        }
    
        if(bindingResult.hasErrors()){
            Usuarios sede = (Usuarios) session.getAttribute("usuario");
            model.addAttribute("asignados", asignacionTiendasRepository.findAsignacionTiendasByStockGreaterThanAndAsignadosSedes_Id_Sede(0,sede));
            model.addAttribute("id2", idAstiendas);
            model.addAttribute("tipodoc", CustomConstants.getTiposDocumento());
            model.addAttribute("msgErrorDevolucion", "ERROR");
            return "sede/asignadoTiendas";
        }else{
            AsignadosSedes as = at.getAsignadosSedes();

            //AsignadosID = gestor - sede - inventario - estado - precio
            //restar stock(Asignado_Tienda) y sumar cantidadactual(Asigandos_sed    es)
            //sumar lo devuelto a cant_gestor(inventario)
            asignacionTiendasRepository.tienda_devolucion(as.getId().getGestor().getIdusuarios(), as.getId().getSede().getIdusuarios(),
                    as.getId().getProductoinventario().getCodigoinventario(), as.getId().getEstadoasignacion(),
                    as.getId().getPrecioventa(),v.getCantDevol(), at.getIdtiendas());

            attr.addFlashAttribute("msg","Producto devuelto exitosamente");

            return "redirect:/sede/AsignadoTienda";

        }

    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<AsignacionTiendas>> getAsignacion(@RequestParam(value = "id1") int id){
        return new ResponseEntity<>(asignacionTiendasRepository.findById(id), HttpStatus.OK);
    }

}
