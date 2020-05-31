package com.example.sw2.controller.gestor;

import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.entity.Artesanos;
import com.example.sw2.entity.Inventario;
import com.example.sw2.entity.Productos;
import com.example.sw2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.YearMonth;
import java.util.Optional;

//REPLANTEAR inventario entity

@Controller
@RequestMapping("/gestor/inventario")
public class InventarioController {
    @Autowired
    InventarioRepository inventarioRepository;
    @Autowired
    CategoriasRepository categoriasRepository;

    @Autowired
    ComunidadesRepository comunidadesRepository;

    @Autowired
    ProductosRepository productosRepository;

    @Autowired
    ArtesanosRepository artesanosRepository;


    @GetMapping(value = {""})
    public String listInv(@ModelAttribute("inventario") Inventario inventario, Model m) {
        m.addAttribute("listaInv", inventarioRepository.findAll());

        return "gestor/inventarioGestor";
    }

    @GetMapping(value = {"/form"})
    public String form(@ModelAttribute("inventario") Inventario inventario, Model m) {
        listasCamposInv(m);
        return "gestor/inventarioGestorForm";
    }


    @PostMapping(value = {"/save"})
    public String save(@ModelAttribute("inventario") Inventario inventario,
                       BindingResult bindingResult, Model m, RedirectAttributes attributes,
                       @RequestParam("conDia") String[] conDiastr,
                       @RequestParam(value = "fechadia", required = false) String fechadia,
                       @RequestParam(value = "fechames", required = false) String fechames) {
        LocalDate fechadiaformat=null;
        YearMonth fechamesformat=null;

        Boolean conDia = conDiastr.length == 2;
        if (conDia){
            if (fechadia.isEmpty()) {
                bindingResult.rejectValue("dia", "error.user", "Ingrese una fecha");
            }else {
                try {
                    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_DATE;
                    fechadiaformat = LocalDate.parse(fechadia, dateTimeFormat);
                    inventario.setFechaDiaFORMAT(fechadiaformat);
                } catch (Exception e) {
                    bindingResult.rejectValue("dia", "error.user", "Ingrese una fecha válida");
                }
            }

        }else {
            if (fechames.isEmpty()) {
                bindingResult.rejectValue("dia", "error.user", "Ingrese una fecha");
            }else {
                try {
                    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM");
                    fechamesformat = YearMonth.parse(fechames, dateTimeFormat);
                    inventario.setFechaMesFORMAT(fechamesformat);
                } catch (Exception e) {
                    bindingResult.rejectValue("dia", "error.user", "Ingrese una fecha válida");
                }
            }
        }

        Optional<Inventario> optionalInventario = inventarioRepository.findInventariosByNumpedido(inventario.getNumpedido());
        if (optionalInventario.isPresent()) {
            bindingResult.rejectValue("numpedido", "error.user", "Este número de pedido ya existe");
        }

        if (bindingResult.hasErrors()) {
            listasCamposInv(m);
            m.addAttribute("listArt", artesanosRepository.findAll());
            m.addAttribute("listProd", productosRepository.findAll());

            return "gestor/inventarioGestorForm";
        } else {


            String codInv = generaCodigo(inventario);
            Optional<Inventario> optionalInventario2 =
                    inventarioRepository.findById(codInv);
            if (optionalInventario2.isPresent()) {
                m.addAttribute("msg", "El código de este producto en el inventario ya existe. Si desea aumentar la cantidad de este producto, revise el inventario.");

                listasCamposInv(m);
                m.addAttribute("listArt", artesanosRepository.findAll());
                m.addAttribute("listProd", productosRepository.findAll());
                return "gestor/inventarioGestorForm";
            }




            /*
            //se necesita checar si hay un inventario idéntico para sumar
            Optional<Inventario> optionalInventario =
                    inventarioRepository.findById(inventario.getCodigoinventario());
            if(optionalInventario.isPresent()){
                Inventario inv = optionalInventario.get();
                inv.setCantidadtotal(inv.getCantidadtotal()+inventario.getCantidadtotal());
                inv.setCantidadgestor(inv.getCantidadgestor()+inventario.getCantidadtotal());
                inventario = inv;
            }
            else {
                inventario.setCantidadgestor(inventario.getCantidadtotal());
            }
            inventarioRepository.save(inventario);*/
            attributes.addFlashAttribute("msg", "LA VALIDACION PERFECTA");

            return "redirect:/gestor/inventario";
        }
    }


    private void listasCamposInv(Model m) {

        m.addAttribute("taman", CustomConstants.getTamanhos());
        m.addAttribute("tipoAdqui", CustomConstants.getTiposAdquisicion());
        m.addAttribute("lineas", CustomConstants.getLineas());
        m.addAttribute("listCat", categoriasRepository.findAll());
        m.addAttribute("listCom", comunidadesRepository.findAll());
    }

    private String generaCodigo(Inventario inv) {
        String cod = inv.getProductos().getCodigolinea()
                + inv.getCategorias().getCodigo()
                + inv.getProductos().getCodigonom()
                + inv.getProductos().getCodigodesc()
                + inv.getCodtamanho()
                + inv.getComunidades().getCodigo();
        if (inv.getCodAdquisicion() == 1) {
            cod += inv.getArtesanos().getCodigo()
                    + inv.getAnho()
                    + inv.getCodmes();
        }
        return cod;
    }


    //Web service
    @ResponseBody
    @GetMapping(value = {"/form/getLinea", "/save/getLinea"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Productos>> getCom(@RequestParam(value = "linea") String linea) {
        return new ResponseEntity<>(productosRepository.findProductosByCodigolinea(linea), HttpStatus.OK);
    }

    //Web service
    @ResponseBody
    @GetMapping(value = {"/form/getArtesanos", "/save/getArtesanos"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artesanos>> getArtesanos(@RequestParam(value = "comunidad") String comunidad) {
        return new ResponseEntity<>(artesanosRepository.findArtesanosByComunidades_Codigo(comunidad), HttpStatus.OK);
    }


}
