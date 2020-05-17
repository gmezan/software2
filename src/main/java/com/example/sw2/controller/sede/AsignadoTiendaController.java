package com.example.sw2.controller.sede;

import com.example.sw2.dto.DatosAsignadosTiendaDto;
import com.example.sw2.entity.AsignacionTiendas;
import com.example.sw2.entity.Inventario;
import com.example.sw2.entity.Tienda;
import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    @GetMapping(value = {"", "/"})
    public String ListaAsignacionTiendas(@ModelAttribute("venta") Ventas venta,
                                         @ModelAttribute("asignados") AsignacionTiendas asignados,
                                         Model model){
        model.addAttribute("asignados", asignacionTiendasRepository.findAll());
        model.addAttribute("listaDatosAsignados", asignacionTiendasRepository.obtenerDatosAsignados());
        return "sede/asignadoTiendas";
    }

    @PostMapping("/registrar")
    public String RegistrarVentas(@ModelAttribute("venta") Ventas venta,
                                  @RequestParam("id") int id,
                                  Model model, RedirectAttributes attr){

        int cantVent = venta.getCantidad();
        String codigo = venta.getInventario().getCodigoinventario();
        asignacionTiendasRepository.stockAsignadoSedeActualizado(codigo, cantVent);
        asignacionTiendasRepository.stockInventarioActualizado(codigo, cantVent);
        attr.addFlashAttribute("msg", "Venta registrada exitosamente");
        ventasRepository.save(venta);
        return "redirect:/sede/AsignadoTienda";
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<AsignacionTiendas>> getAsignacion(@RequestParam(value = "id") int id){
        return new ResponseEntity<>(asignacionTiendasRepository.findById(id), HttpStatus.OK);
    }





}
