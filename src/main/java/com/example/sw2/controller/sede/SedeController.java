package com.example.sw2.controller.sede;

import com.example.sw2.constantes.AsignadosSedesId;
import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.constantes.VentasId;
import com.example.sw2.entity.*;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/sede")
public class SedeController {

    @Autowired
    AsignadosSedesRepository asignadosSedesRepository;

    @Autowired
    UsuariosRepository usuariosRepository;

    @Autowired
    InventarioRepository inventarioRepository;
    @Autowired
    VentasRepository ventasRepository;
    @Autowired
    AsignacionTiendasRepository asignacionTiendasRepository;

    @GetMapping(value = {"/", ""})
    public String init() {
        return "redirect:/sede/tienda";
    }

    @GetMapping("productosPorConfirmar")
    public String productosPorConfirmar(HttpSession session, Model model) {

        Usuarios sede = (Usuarios) session.getAttribute("usuario");

        model.addAttribute("listaProductosPorConfirmar", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
        return "sede/ListaProductosPorConfirmar";

    }

    @GetMapping("productosConfirmados")
    public String productosConfirmados(@ModelAttribute("venta") Ventas ventas,
            @ModelAttribute("asignaciontiendas") AsignacionTiendas asignacionTiendas,HttpSession session, Model model) {

        Usuarios sede = (Usuarios) session.getAttribute("usuario");

        model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
        return "sede/ListaProductosConfirmados";

    }

    @PostMapping("registrarVenta")
    public String registrarVenta(@ModelAttribute("venta") @Valid Ventas ventas,
                                 BindingResult bindingResult, RedirectAttributes attr, HttpSession session, Model model) {

        if (bindingResult.hasErrors()) {
            Usuarios sede = (Usuarios) session.getAttribute("usuario");
            model.addAttribute("venta", ventas);
            model.addAttribute("msgError", "ERROR");
            model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
            return "sede/ListaProductosConfirmados";
        } else {
            Optional<Ventas> optVenta = ventasRepository.findById(new VentasId(ventas.getId().getTipodocumento(), ventas.getId().getNumerodocumento()));

            if (optVenta.isPresent()) {
                Usuarios sede = (Usuarios) session.getAttribute("usuario");
                model.addAttribute("msgBoleta", "El codigo de esta venta ya ha sido registrada");
                model.addAttribute("msgError", "ERROR");
                model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
                return "sede/ListaProductosConfirmados";
            } else {
                attr.addFlashAttribute("msgExito", "Venta registrada exitosamente");
                ventas.setFechacreacion(LocalDateTime.now());
                ventasRepository.save(ventas);
                return "redirect:/sede/productosConfirmados";
            }
        }
    }


    @PostMapping("asignar")
    public String asignarProducto(@ModelAttribute("asignaciontiendas") @Valid AsignacionTiendas asignacionTiendas, BindingResult bindingResult,
                                  RedirectAttributes attr, HttpSession session, Model model) {

        if (bindingResult.hasErrors()) {
            Usuarios sede = (Usuarios) session.getAttribute("usuario");
            model.addAttribute("asignaciontiendas", asignacionTiendas);
            model.addAttribute("msgError", "ERROR");
            model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
            return "sede/ListaProductosConfirmados";
        } else {
            Optional<AsignacionTiendas> asignTiendasOpt = asignacionTiendasRepository.findById(asignacionTiendas.getIdtiendas());

            if (asignTiendasOpt.isPresent()) {
                Usuarios sede = (Usuarios) session.getAttribute("usuario");
                model.addAttribute("msgError", "ERROR");
                model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
                return "sede/ListaProductosConfirmados";
            } else {
                attr.addFlashAttribute("msgExito", "Producto asignado exitosamente");
                asignacionTiendas.setFechacreacion(LocalDateTime.now());
                asignacionTiendasRepository.save(asignacionTiendas);
                return "redirect:/sede/productosConfirmados";
            }
        }
    }

    @PostMapping("/confirmarRecepcion")
    public String confirmarRecepcion(AsignadosSedesId id, RedirectAttributes attr) {
/*
        AsignadosSedesId id = new AsignadosSedesId(usuariosRepository.findById(idgestor).get(), usuariosRepository.findById(idsede).get(),
                inventarioRepository.findById(idproductoinv).get(),
                idestadoasign, idprecioventa);*/

        Optional<AsignadosSedes> asignadosSedesOptional = asignadosSedesRepository.findById(id);
        if (asignadosSedesOptional.isPresent()) {
            AsignadosSedesId idNew = new AsignadosSedesId(id.getGestor(), id.getSede(),
                    id.getProductoinventario(), CustomConstants.ESTADO_RECIBIDO_POR_SEDE, id.getPrecioventa());
            AsignadosSedes newAsignadosSedes = new AsignadosSedes(idNew, asignadosSedesOptional.get());
            attr.addFlashAttribute("msgExito", "Se ha registrado la recepcion correctamente");
            asignadosSedesRepository.save(newAsignadosSedes);
            asignadosSedesRepository.deleteById(id);
        }
        return "redirect:/sede/productosPorConfirmar";
    }

    @PostMapping("registrarProblema")
    public String registrarProblema(@RequestParam(value = "mensaje") String mensaje,
                                    @RequestParam(value = "idgestor1") int idgestor,
                                    @RequestParam(value = "idsede1") int idsede,
                                    @RequestParam(value = "idproductoinv1") String idproductoinv,
                                    @RequestParam(value = "idestadoasign1") int idestadoasign,
                                    @RequestParam(value = "idprecioventa1") Float idprecioventa, RedirectAttributes attr) {

        AsignadosSedesId id = new AsignadosSedesId(usuariosRepository.findById(idgestor).get(), usuariosRepository.findById(idsede).get(),
                inventarioRepository.findById(idproductoinv).get(),
                idestadoasign, idprecioventa);

        Optional<AsignadosSedes> asignadosSedesOptional = asignadosSedesRepository.findById(id);

        if (asignadosSedesOptional.isPresent()) {

            AsignadosSedesId idNew = new AsignadosSedesId(id.getGestor(), id.getSede(),
                    id.getProductoinventario(), 3, id.getPrecioventa());
            asignadosSedesRepository.deleteById(id);
            AsignadosSedes asignadosSedes = asignadosSedesOptional.get();
            asignadosSedes.setId(idNew);
            asignadosSedes.setMensaje(mensaje);
            attr.addFlashAttribute("msgExito", "Se ha reportado el problema correctamente");

            asignadosSedesRepository.save(asignadosSedes);
        }
        return "redirect:/sede/productosPorConfirmar";
    }




    //Web service
    @ResponseBody
    @GetMapping(value = {"/productosPorConfirmar/get"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<AsignadosSedes>> getAsignsede(@RequestParam(value = "idgestor") int idgestor,
                                                                 @RequestParam(value = "idsede") int idsede,
                                                                 @RequestParam(value = "idproductoinv") String idproductoinv,
                                                                 @RequestParam(value = "idestadoasign") int idestadoasign,
                                                                 @RequestParam(value = "idprecioventa") Float idprecioventa) {

        return new ResponseEntity<>(asignadosSedesRepository.findById(new AsignadosSedesId(usuariosRepository.findById(idgestor).get(),
                usuariosRepository.findById(idsede).get(),
                inventarioRepository.findById(idproductoinv).get(),
                idestadoasign, idprecioventa)), HttpStatus.OK);
    }

    //Web service
    @ResponseBody
    @PostMapping(value = "/productosPorConfirmar/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, String>> getAsignsedePost(@RequestBody AsignadosSedesId asignadosSedesId) {

        return new ResponseEntity<>(new HashMap<String, String>() {{
            asignadosSedesId.setProductoinventario(inventarioRepository.findByCodigoinventario(asignadosSedesId.getProductoinventario().getCodigoinventario()));
            asignadosSedesRepository.findAll();
            AsignadosSedes asignadosSedes = asignadosSedesRepository.findById(asignadosSedesId).orElse(null);
            put("idgestor", Integer.toString(asignadosSedesId.getGestor().getIdusuarios()));
            put("idsede", Integer.toString(asignadosSedesId.getSede().getIdusuarios()));
            put("idproductoinv", asignadosSedesId.getProductoinventario().getCodigoinventario());
            put("idestadoasign", Integer.toString(asignadosSedesId.getEstadoasignacion()));
            put("idprecioventa", Float.toString(asignadosSedesId.getPrecioventa()));
            put("codinv", asignadosSedesId.getProductoinventario().getCodigoinventario());
            put("fechaenvio", asignadosSedes != null ? asignadosSedes.getFechaenvio().toString() : null);
            put("producto", asignadosSedesId.getProductoinventario().getProductos().getNombre());
            put("precioventa", asignadosSedes != null ? Float.toString(asignadosSedesId.getPrecioventa()) : null);
            put("color", asignadosSedesId.getProductoinventario().getColor());
            put("tamanho", asignadosSedesId.getProductoinventario().getTamanho());
            put("stock", asignadosSedes != null ? String.valueOf(asignadosSedes.getStock()) : null);
            put("foto", asignadosSedesId.getProductoinventario().getFoto());
            put("comunidades", asignadosSedesId.getProductoinventario().getComunidades().getNombre());
        }},
                HttpStatus.OK);
    }

}
    