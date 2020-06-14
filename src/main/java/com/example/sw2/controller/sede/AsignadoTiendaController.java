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
        model.addAttribute("asignados", asignacionTiendasRepository.findAsignacionTiendasByAsignadosSedes_Id_Sede(sede));
        model.addAttribute("tipodoc", CustomConstants.getTiposDocumento());
        return "sede/asignadoTiendas";
    }

    @PostMapping("/registrar")
    public String RegistrarVentas(@ModelAttribute("ventas") @Valid Ventas venta,
                                  BindingResult bindingResult, HttpSession session,
                                  @RequestParam("id1") int idAstiendas,
                                  Model model, RedirectAttributes attr){

        Optional<AsignacionTiendas> optaTienda = asignacionTiendasRepository.findById(idAstiendas);
        AsignacionTiendas aTienda = optaTienda.get();

        if(venta.getCantidad() > aTienda.getStock()){
            bindingResult.rejectValue("cantidad", "error.user","La cantidad no puede ser mayor al stock de la tienda");
        }
        if(venta.getId().getNumerodocumento().equals("")){
            bindingResult.rejectValue("id.numerodocumento", "error.user","Este campo no puede estar vacío");
        }

        if(bindingResult.hasErrors()){
            Usuarios sede = (Usuarios) session.getAttribute("usuario");
            model.addAttribute("asignados", asignacionTiendasRepository.findAsignacionTiendasByAsignadosSedes_Id_Sede(sede));
            model.addAttribute("id1", idAstiendas);
            model.addAttribute("tipodoc", CustomConstants.getTiposDocumento());
            model.addAttribute("msgErrorRegistrar", "ERROR");
            return "sede/asignadoTiendas";


        }else{
            venta.setFecha(LocalDate.now());
            venta.setFechacreacion(LocalDateTime.now());
            ventasRepository.save(venta);
            //actualizar stock(Asignados_sedes) cant_total(inventario)
            asignacionTiendasRepository.registrar_venta_tienda(aTienda.getAsignadosSedes().getId().getGestor().getIdusuarios(),
                    venta.getVendedor().getIdusuarios(), venta.getInventario().getCodigoinventario(),
                    2,venta.getPrecioventa(), venta.getCantidad(), idAstiendas);
            if(aTienda.getStock() == 0){
                asignacionTiendasRepository.deleteById(aTienda.getIdtiendas());
            }
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

        if(v.getCantDevol() >= 0){

            if(v.getCantDevol() > at.getStock()){
                bindingResult.rejectValue("cantDevol", "error.user","La cantidad no puede ser mayor al stock actual de la tienda");
            }
            bindingResult.rejectValue("cantDevol", "error.user","La cantidad tiene que ser mayor a 0");

            Usuarios sede = (Usuarios) session.getAttribute("usuario");
            model.addAttribute("asignados", asignacionTiendasRepository.findAsignacionTiendasByAsignadosSedes_Id_Sede(sede));
            model.addAttribute("id2", idAstiendas);
            model.addAttribute("tipodoc", CustomConstants.getTiposDocumento());
            model.addAttribute("msgErrorDevolucion", "ERROR");
            return "sede/asignadoTiendas";
        }else{
            AsignadosSedes as = at.getAsignadosSedes();

            //restar stock(Asignado_Tienda) y sumar cantidadactual(Asigandos_sedes)

            asignacionTiendasRepository.devol_tienda(as.getId().getGestor().getIdusuarios(), as.getId().getSede().getIdusuarios(),
                    as.getId().getProductoinventario().getCodigoinventario(), as.getId().getEstadoasignacion(),
                    as.getId().getPrecioventa(),v.getCantDevol(), at.getIdtiendas());
            if(at.getStock() == 0){
                asignacionTiendasRepository.deleteById(at.getIdtiendas());
            }
            //sumar lo devuelto a cant_gestor del inventario
            attr.addFlashAttribute("msg","Producto devuelto exitosamente");

            return "redirect:/sede/AsignadoTienda";

        }

        //AsignadosID = gestor - sede - inventario - estado - precio
        //sede - producto - estado
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<AsignacionTiendas>> getAsignacion(@RequestParam(value = "id1") int id){
        return new ResponseEntity<>(asignacionTiendasRepository.findById(id), HttpStatus.OK);
    }

}
