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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

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
    @Autowired
    TiendaRepository tiendaRepository;

    @GetMapping(value = {"/", ""})
    public String init() {
        return "redirect:/sede/productosPorConfirmar";
    }

    @GetMapping("productosPorConfirmar")
    public String productosPorConfirmar(HttpSession session, Model model) {

        Usuarios sede = (Usuarios) session.getAttribute("usuario");

        model.addAttribute("listaProductosPorConfirmar", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
        return "sede/ListaProductosPorConfirmar";

    }

    @GetMapping("productosConfirmados")
    public String productosConfirmados(@ModelAttribute("venta") Ventas ventas,
                                       @ModelAttribute("asignaciontiendas") AsignacionTiendas asignacionTiendas, HttpSession session, Model model) {

        Usuarios sede = (Usuarios) session.getAttribute("usuario");

        model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
        model.addAttribute("listaTiendas", tiendaRepository.findAll());

        return "sede/ListaProductosConfirmados";
    }

    @PostMapping("registrarVenta")
    public String registrarVenta(@ModelAttribute("venta") @Valid Ventas ventas,
                                 BindingResult bindingResult,
                                 @ModelAttribute("asignaciontiendas") AsignacionTiendas asignacionTiendas,
                                 @RequestParam(value = "idgestor") int idgestor,
                                 @RequestParam(value = "vendedor") int idsede,
                                 @RequestParam(value = "inventario") String idproductoinv,
                                 @RequestParam(value = "idestadoasign") int idestadoasign,
                                 @RequestParam(value = "idprecioventa") Float idprecioventa,
                                 RedirectAttributes attr, HttpSession session, Model model) {

        AsignadosSedesId id = new AsignadosSedesId(usuariosRepository.findById(idgestor).get(), usuariosRepository.findById(idsede).get(),
                inventarioRepository.findById(idproductoinv).get(),
                idestadoasign, idprecioventa);

        Optional<AsignadosSedes> asignadosSedesOptional = asignadosSedesRepository.findById(id);
        AsignadosSedes asignadosSedes = asignadosSedesOptional.get();

        if (asignadosSedes.getCantidadactual()==0){
            attr.addFlashAttribute("msgNoVenta", "No se puede registrar la venta de este producto, dado que la cantidad actual es 0.");
            return "redirect:/sede/productosConfirmados";
        }

        if (ventas.getCantidad() <= 0) {
            bindingResult.rejectValue("cantidad", "error.user", "Ingrese una cantidad valida");

        } else {

            if (ventas.getCantidad() > asignadosSedes.getCantidadactual()) {
                bindingResult.rejectValue("cantidad", "error.user", "La cantidad vendida no puede ser mayor a la cantidad actual de la sede");
            }
        }

        if (!ventas.getNombrecliente().isEmpty()) {
            if (Pattern.compile("[0-9]").matcher(ventas.getNombrecliente()).find()) {
                bindingResult.rejectValue("nombrecliente", "error.user", "No se permiten valores numéricos.");
            }
            if (ventas.getNombrecliente().trim().length() == 0) {
                bindingResult.rejectValue("nombrecliente", "error.user", "Ingrese un nombre válido.");
            }
        }

        if (!ventas.getLugarventa().isEmpty()) {
            if (Pattern.compile("[0-9]").matcher(ventas.getLugarventa()).find()) {
                bindingResult.rejectValue("lugarventa", "error.user", "No se permiten valores numéricos.");
            }
            if (ventas.getLugarventa().trim().length() == 0) {
                bindingResult.rejectValue("lugarventa", "error.user", "Ingrese un lugar de venta válido.");
            }
        }

        Usuarios sede = (Usuarios) session.getAttribute("usuario");

        if (bindingResult.hasErrors()) {
            model.addAttribute("venta", ventas);
            model.addAttribute("idgestor", idgestor);
            model.addAttribute("vendedor", idsede);
           // model.addAttribute("codinv", idproductoinv);
            model.addAttribute("inventario", idproductoinv);
            model.addAttribute("idestadoasign", idestadoasign);
            model.addAttribute("idprecioventa", idprecioventa);
            model.addAttribute("listaTiendas", tiendaRepository.findAll());
            model.addAttribute("msgError_V", "ERROR");
            model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
            return "sede/ListaProductosConfirmados";
        } else {
            Optional<Ventas> optVenta = ventasRepository.findById(new VentasId(ventas.getId().getTipodocumento(), ventas.getId().getNumerodocumento()));

            if (optVenta.isPresent()) {
                model.addAttribute("msgBoleta", "El numero y tipo de documento de esta venta ya ha sido registrada anteriormente");
                model.addAttribute("id32", idproductoinv);
                model.addAttribute("msgError_V", "ERROR");
                model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
                return "sede/ListaProductosConfirmados";

            } else {
                ventas.setFechacreacion(LocalDateTime.now());
                ventasRepository.save(ventas);

                int StockActual = asignadosSedes.getStock() - ventas.getCantidad();
                asignadosSedes.setCantidadactual(asignadosSedes.getCantidadactual() - ventas.getCantidad());
                asignadosSedes.setStock(StockActual);

                Inventario inventario = inventarioRepository.findByCodigoinventario(asignadosSedes.getId().getProductoinventario().getCodigoinventario());
                inventario.setCantidadtotal(inventario.getCantidadtotal() - ventas.getCantidad());

                asignadosSedesRepository.save(asignadosSedes);
                inventarioRepository.save(inventario);

                attr.addFlashAttribute("msgExito", "Venta registrada exitosamente");
                return "redirect:/sede/productosConfirmados";
            }
        }
    }


    @PostMapping("asignar")
    public String asignarProducto(@ModelAttribute("asignaciontiendas") @Valid AsignacionTiendas asignacionTiendas,
                                  BindingResult bindingResult,
                                  @ModelAttribute("venta") Ventas venta,
                                  @RequestParam(value = "gestor") int idgestor,
                                  @RequestParam(value = "sede") int idsede,
                                  @RequestParam(value = "inventario1") String idproductoinv,
                                  @RequestParam(value = "estadoasignacion") int idestadoasign,
                                  @RequestParam(value = "precioventa") Float idprecioventa,
                                  BindingResult bindingResult2,
                                  RedirectAttributes attr, HttpSession session, Model model) {

        AsignadosSedesId id = new AsignadosSedesId(usuariosRepository.findById(idgestor).get(), usuariosRepository.findById(idsede).get(),
                inventarioRepository.findById(idproductoinv).get(),
                idestadoasign, idprecioventa);

        Optional<AsignadosSedes> asignadosSedesOptional = asignadosSedesRepository.findById(id);
        AsignadosSedes asignadosSedes = asignadosSedesOptional.get();

        if (asignadosSedes.getCantidadactual()==0){
            attr.addFlashAttribute("msgNoAsignar", "No se puede asignar este producto, dado que la cantidad actual es 0.");
            return "redirect:/sede/productosConfirmados";
        }

        if (asignacionTiendas.getStock() <= 0) {
            bindingResult.rejectValue("stock", "error.user", "Ingrese una cantidad valida");
        } else {

            if (asignacionTiendas.getStock() > asignadosSedes.getCantidadactual()) {
                bindingResult.rejectValue("stock", "error.user", "La cantidad asignada no puede ser mayor a la cantidad actual de la sede");
            }
        }

        if (bindingResult.hasErrors()) {
            Usuarios sede = (Usuarios) session.getAttribute("usuario");
            model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
            model.addAttribute("listaTiendas", tiendaRepository.findAll());
            model.addAttribute("msgError_A", "ERROR");
            model.addAttribute("gestor", idgestor);
            model.addAttribute("sede", idsede);
            // model.addAttribute("codinv", idproductoinv);
            model.addAttribute("inventario1", idproductoinv);
            model.addAttribute("estadoasignacion", idestadoasign);
            model.addAttribute("precioventa", idprecioventa);
            model.addAttribute("venta", venta);

            return "sede/ListaProductosConfirmados";
        } else {


            List<AsignacionTiendas> asignacionTiendasListOld = asignacionTiendasRepository.findAsignacionTiendasByTiendaAndAsignadosSedes(asignacionTiendas.getTienda(), asignacionTiendas.getAsignadosSedes());
            if (asignacionTiendasListOld.isEmpty()) {
                asignacionTiendas.setAsignadosSedes(asignadosSedes);
                asignacionTiendas.setFechacreacion(LocalDateTime.now());
                asignacionTiendasRepository.save(asignacionTiendas);
            } else {
                AsignacionTiendas asigTiendaOld = asignacionTiendasListOld.get(0);
                asigTiendaOld.setStock(asignacionTiendas.getStock() + asigTiendaOld.getStock());
                asigTiendaOld.setFechaasignacion(asignacionTiendas.getFechaasignacion());
                asigTiendaOld.setAsignadosSedes(asignadosSedes);
                asignacionTiendasRepository.save(asigTiendaOld);
            }
            asignadosSedes.setCantidadactual(asignadosSedes.getCantidadactual() - asignacionTiendas.getStock());
            asignadosSedesRepository.save(asignadosSedes);
            attr.addFlashAttribute("msgExito", "Producto asignado exitosamente");
            return "redirect:/sede/productosConfirmados";

        }
    }

    @PostMapping("/confirmarRecepcion")
    public String confirmarRecepcion(AsignadosSedesId id, RedirectAttributes attr) {

        Optional<AsignadosSedes> asignadosSedesOptional = asignadosSedesRepository.findById(id);
        if (asignadosSedesOptional.isPresent()) {

            AsignadosSedesId idNew = new AsignadosSedesId(id.getGestor(), id.getSede(),
                    id.getProductoinventario(), CustomConstants.ESTADO_RECIBIDO_POR_SEDE, id.getPrecioventa());
            AsignadosSedes newAsignadosSedes = new AsignadosSedes(idNew, asignadosSedesOptional.get());
            attr.addFlashAttribute("msgExito", "Se ha registrado la recepcion correctamente");
            asignadosSedesRepository.save(newAsignadosSedes);
            newAsignadosSedes.setFechacreacion(LocalDateTime.now());
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

    @PostMapping("/devolucion")
    public String devolucionSede(@ModelAttribute("asignaciontiendas") AsignacionTiendas asignacionTiendas,
                                 BindingResult bindingResult,
                                 @ModelAttribute("venta") Ventas ventas,
                                 @RequestParam(value = "id13") int idgestor,
                                 @RequestParam(value = "id23") int idsede,
                                 @RequestParam(value = "id33") String idproductoinv,
                                 @RequestParam(value = "id43") int idestadoasign,
                                 @RequestParam(value = "id53") Float idprecioventa,
                                 HttpSession session,
                                 Model model, RedirectAttributes attr) {

        AsignadosSedesId id = new AsignadosSedesId(usuariosRepository.findById(idgestor).get(), usuariosRepository.findById(idsede).get(),
                inventarioRepository.findById(idproductoinv).get(),
                idestadoasign, idprecioventa);

        Optional<AsignadosSedes> asignadosSedesOptional = asignadosSedesRepository.findById(id);
        AsignadosSedes asignadosSedes = asignadosSedesOptional.get();
        System.out.println("Encontrado");

        if (asignadosSedes.getCantidadactual()==0){
            attr.addFlashAttribute("msgNoDevolucion", "No se puede devolver este producto, dado que la cantidad actual es 0.");
            return "redirect:/sede/productosConfirmados";
        }

        if (ventas.getCantDevol() <= 0) {
            bindingResult.rejectValue("cantDevol", "error.user", "Ingrese una cantidad valida");
        } else {

            if (ventas.getCantDevol() > asignadosSedes.getCantidadactual()) {
                bindingResult.rejectValue("cantDevol", "error.user", "La cantidad devuelta no puede ser mayor a la cantidad actual de la sede");
            }
        }

        if (bindingResult.hasErrors()) {

            Usuarios sede = (Usuarios) session.getAttribute("usuario");
            model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
            model.addAttribute("listaTiendas", tiendaRepository.findAll());
            model.addAttribute("msgError_D", "ERROR");

            model.addAttribute("venta", ventas);
            model.addAttribute("id13", idgestor);
            model.addAttribute("id23", idsede);
            model.addAttribute("id33", idproductoinv);
            model.addAttribute("id43", idestadoasign);
            model.addAttribute("id53", idprecioventa);

        } else {
            AsignadosSedesId idNew = new AsignadosSedesId(id.getGestor(), id.getSede(),
                    id.getProductoinventario(), CustomConstants.ESTADO_DEVUELTO_POR_SEDE, id.getPrecioventa());
            Optional<AsignadosSedes> asignSedes = asignadosSedesRepository.findById(idNew);

            System.out.println("Encontrado2");

            if (asignSedes.isPresent()) {
                AsignadosSedes asignadosSedesNew = asignSedes.get();
                asignadosSedesNew.setId(idNew);
                asignadosSedesNew.setCantidadactual(asignadosSedesNew.getCantidadactual() + ventas.getCantDevol());
                asignadosSedesNew.setStock(asignadosSedesNew.getStock() + ventas.getCantDevol());
                asignadosSedesRepository.save(asignadosSedesNew);
            } else {
                AsignadosSedes nuevoAs = new AsignadosSedes();
                nuevoAs.setId(idNew);
                nuevoAs.setFechaenvio(asignadosSedes.getFechaenvio());
                nuevoAs.setFechacreacion(LocalDateTime.now());
                nuevoAs.setCantidadactual(ventas.getCantDevol());
                nuevoAs.setStock(ventas.getCantDevol());
                asignadosSedesRepository.save(nuevoAs);
            }

            asignadosSedes.setCantidadactual(asignadosSedes.getCantidadactual() - ventas.getCantDevol());
            asignadosSedesRepository.save(asignadosSedes);

            attr.addFlashAttribute("msgExito", "Producto devuelto exitosamente");


        }
        return "redirect:/sede/productosConfirmados";
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
    