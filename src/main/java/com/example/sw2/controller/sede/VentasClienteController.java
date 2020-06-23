package com.example.sw2.controller.sede;

import com.example.sw2.constantes.VentasId;
import com.example.sw2.entity.AsignacionTiendas;
import com.example.sw2.entity.Tienda;
import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.AsignacionTiendasRepository;
import com.example.sw2.repository.TiendaRepository;
import com.example.sw2.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sede/ventasCliente")
public class VentasClienteController {

    @Autowired
    VentasRepository ventasRepository;
    @Autowired
    TiendaRepository tiendaRepository;
    @Autowired
    AsignacionTiendasRepository asignacionTiendasRepository;

    @GetMapping(value = {"", "/"})
    public String ListVentasCliente(@ModelAttribute("ventas") Ventas ventas,
                                    Model model){

        model.addAttribute("listaVentas", ventasRepository.findAll());
        return "sede/ventasPorCliente";
    }

    /*
    @GetMapping("/borrar")
    public String borrarVenta(@RequestParam("id1") String numdoc,
                              @RequestParam("id2") int tipodoc,
                              @RequestParam("id3") String nombre,
                              @RequestParam("id4") String direccion,
                              @RequestParam("id5") int ruc,
                              @RequestParam("id6") int cant,
                              RedirectAttributes attr) {


        Optional<Ventas> optVenta = ventasRepository.findById(new VentasId(tipodoc,numdoc));
        if (optVenta.isPresent()) {
            Ventas v = optVenta.get(); //Se obtiene la venta

            Optional<Tienda> optTienda = tiendaRepository.findByNombreAndDireccionAndRuc(v.getNombrecliente(),
                    v.getLugarventa(),v.getRucdni());
            //Se verifica si la venta es de una tienda
            if(optTienda.isPresent()){
                //Se obtiene la tienda
                Tienda t = optTienda.get();
                Optional<AsignacionTiendas> optAt = asignacionTiendasRepository.findByTiendaAndAsignadosSedes_Id_Precioventa(t.getIdtienda(), v.getPrecioventa());
                //Verificación si aún existe la fila en Asignados a Tienda
                if(optAt.isPresent()){
                    AsignacionTiendas at = optAt.get();
                    int stockA = at.getStock();
                    at.setStock(stockA + v.getCantidad());
                }

            }else{

            }

            ventasRepository.deleteById(new VentasId(tipodoc, numdoc));
            attr.addFlashAttribute("msgBorrado","Venta eliminada exitosamente");
        }
        return "redirect:/sede/ventasCliente";

    }

     */


}
