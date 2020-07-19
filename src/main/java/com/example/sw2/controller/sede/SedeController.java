package com.example.sw2.controller.sede;

import com.example.sw2.Dao.StorageServiceDao;
import com.example.sw2.constantes.AsignadosSedesId;
import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.constantes.VentasId;
import com.example.sw2.entity.*;
import com.example.sw2.repository.*;
import com.example.sw2.utils.CustomMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.example.sw2.constantes.CustomConstants.MediosDePago;

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
    @Autowired
    StorageServiceDao storageServiceDao;
    @Autowired
    CustomMailService customMailService;

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
        model.addAttribute("mediosDePago",MediosDePago);
        return "sede/ListaProductosConfirmados";
    }

    @PostMapping("registrarVenta")
    public String registrarVenta(@ModelAttribute("venta") @Valid Ventas ventas,
                                 BindingResult bindingResult,
                                 @ModelAttribute("asignaciontiendas") AsignacionTiendas asignacionTiendas,
                                 @RequestParam(value = "idgestor") String idgestorstr,
                                 @RequestParam(value = "idestadoasign") String idestadoasignstr,
                                 RedirectAttributes attr, HttpSession session, Model model,
                                 @RequestParam(name = "foto1", required = false) MultipartFile multipartFile) {
        StorageServiceResponse s2;
        int idgestor;
        int idestadoasign;
        try{
            idgestor= Integer.parseInt(idgestorstr);
            idestadoasign = Integer.parseInt(idestadoasignstr);
        }catch(Exception e){
            attr.addFlashAttribute("msgNoVenta", "Error al encontrar el producto");
            return "redirect:/sede/productosConfirmados";
        }

        AsignadosSedes asignadosSedes = null;

        if(!usuariosRepository.findById(idgestor).isPresent()){
            attr.addFlashAttribute("msgNoVenta", "Error al encontrar el producto");
            return "redirect:/sede/productosConfirmados";
        }


        if (!(bindingResult.hasFieldErrors("precioventa") || bindingResult.hasFieldErrors("inventario") || bindingResult.hasFieldErrors("vendedor"))) {

            AsignadosSedesId id = new AsignadosSedesId(usuariosRepository.findById(idgestor).get(), ventas.getVendedor(),
                    ventas.getInventario(),
                    idestadoasign, ventas.getPrecioventa().floatValue());

            Optional<AsignadosSedes> asignadosSedesOptional = asignadosSedesRepository.findById(id);
            if (asignadosSedesOptional.isPresent()) {
                asignadosSedes = asignadosSedesOptional.get();
                if (asignadosSedes.getCantidadactual() == 0) {
                    attr.addFlashAttribute("msgNoVenta", "No se puede registrar la venta de este producto, dado que la cantidad actual es 0.");
                    return "redirect:/sede/productosConfirmados";
                }
                if (!bindingResult.hasFieldErrors("cantidad")) {
                    if (ventas.getCantidad() > asignadosSedes.getCantidadactual()) {
                        bindingResult.rejectValue("cantidad", "error.user", "La cantidad vendida no puede ser mayor a la cantidad actual de la sede");
                    }
                }
            } else {
                attr.addFlashAttribute("msgNoVenta", "Error al encontrar el producto");
                return "redirect:/sede/productosConfirmados";
            }
        }
        if (!ventas.getRucdni().isEmpty() && !bindingResult.hasFieldErrors("rucdni")) {
            if (!(ventas.getRucdni().length() == 8 || ventas.getRucdni().length() == 11)) {
                bindingResult.rejectValue("rucdni", "error.user", "Ingrese un Ruc(11 dígitos) / DNI(8 dígitos) válido.");
            }
        }


        Usuarios sede = (Usuarios) session.getAttribute("usuario");

        if (ventas.getConfirmado() && !ventas.getId().validateNumeroDocumento()){
                bindingResult.rejectValue("id.numerodocumento","error.user","Ingrese un numero de documento valido");
            if (!(ventas.getMediopago()!=null && ventas.getMediopago()>0 && ventas.getMediopago()<(MediosDePago.size()+1))){
                bindingResult.rejectValue("mediopago","error.user","Ingrese un medio de pago correcto");
            }
        }

        if (!bindingResult.hasFieldErrors("id") && ventas.getConfirmado()) {
            Optional<Ventas> optVenta = ventasRepository.findById(ventas.getId());

            if (optVenta.isPresent()) {
                model.addAttribute("msgBoleta", "El numero y tipo de documento de esta venta ya ha sido registrada anteriormente");
                bindingResult.rejectValue("id", "error.user", "");
            }

        }
        if (bindingResult.hasFieldErrors("id") || bindingResult.hasFieldErrors("rucdni") || bindingResult.hasFieldErrors("nombrecliente") || bindingResult.hasFieldErrors("lugarventa") || bindingResult.hasFieldErrors("fecha") || bindingResult.hasFieldErrors("cantidad")) {
            model.addAttribute("idgestor", idgestor);
            model.addAttribute("idestadoasign", idestadoasign);
            model.addAttribute("listaTiendas", tiendaRepository.findAll());
            model.addAttribute("msgError_V", "ERROR");
            model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
            model.addAttribute("mediosDePago",MediosDePago);
            return "sede/ListaProductosConfirmados";
        } else {
            if(multipartFile!=null && !multipartFile.isEmpty()){
                try {
                    s2 = storageServiceDao.store(ventas,multipartFile);
                } catch (IOException e) {
                    s2 = new StorageServiceResponse("error","Error subiendo el archivo");
                }
                if (!s2.isSuccess()) {
                    model.addAttribute("idgestor", idgestor);
                    model.addAttribute("idestadoasign", idestadoasign);
                    model.addAttribute("listaTiendas", tiendaRepository.findAll());
                    model.addAttribute("msgError_V", "ERROR");
                    model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
                    model.addAttribute("mediosDePago",MediosDePago);
                    return "sede/ListaProductosConfirmados";
                }
            }
            ventasRepository.save(ventas);
            int StockActual = asignadosSedes.getStock() - ventas.getCantidad();
            asignadosSedes.setCantidadactual(asignadosSedes.getCantidadactual() - ventas.getCantidad());
            asignadosSedes.setStock(StockActual);
            Inventario inventario = inventarioRepository.findByCodigoinventario(asignadosSedes.getId().getProductoinventario().getCodigoinventario());
            inventario.setCantidadtotal(inventario.getCantidadtotal() - ventas.getCantidad());
            asignadosSedesRepository.save(asignadosSedes);
            inventarioRepository.save(inventario);
            try {
                if (asignadosSedes.getStock() ==0){
                    customMailService.sendStockAlert(asignadosSedes);
                }
            } catch (MessagingException | IOException  e) {
                e.printStackTrace();
            }
            attr.addFlashAttribute("msgExito", "Venta registrada exitosamente");
            return "redirect:/sede/productosConfirmados";
        }

    }


    @PostMapping("asignar")
    public String asignarProducto(@ModelAttribute("asignaciontiendas") @Valid AsignacionTiendas asignacionTiendas,
                                  BindingResult bindingResult,
                                  @ModelAttribute("venta") Ventas venta,
                                  RedirectAttributes attr, HttpSession session, Model model) {

        Optional<AsignadosSedes> asignadosSedesOptional = asignadosSedesRepository.findById(asignacionTiendas.getAsignadosSedes().getId());

        if (asignadosSedesOptional.isPresent()) {
            AsignadosSedes asignadosSedes = asignadosSedesOptional.get();

            if (asignadosSedes.getCantidadactual() == 0) {
                attr.addFlashAttribute("msgNoAsignar", "No se puede asignar este producto, dado que la cantidad actual es 0.");
                return "redirect:/sede/productosConfirmados";
            }

            if (asignacionTiendas.getStock() < 0) {
                bindingResult.rejectValue("stock", "error.user", "Ingrese una cantidad valida");
            } else {
                if (asignacionTiendas.getStock() > asignadosSedes.getCantidadactual()) {
                    bindingResult.rejectValue("stock", "error.user", "La cantidad asignada no puede ser mayor a la cantidad actual de la sede");
                }
            }

            if (bindingResult.hasErrors() ) {
                model.addAttribute("cantAsign", asignadosSedes.getCantidadactual());
                Usuarios sede = (Usuarios) session.getAttribute("usuario");
                model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
                model.addAttribute("listaTiendas", tiendaRepository.findAll());
                model.addAttribute("msgError_A", "ERROR");
                model.addAttribute("mediosDePago",MediosDePago);
                return "sede/ListaProductosConfirmados";

            } else {

                List<AsignacionTiendas> asignacionTiendasListOld = asignacionTiendasRepository.findAsignacionTiendasByTiendaAndAsignadosSedes(asignacionTiendas.getTienda(), asignacionTiendas.getAsignadosSedes());
                if (asignacionTiendasListOld.isEmpty()) {
                    asignacionTiendas.setAsignadosSedes(asignadosSedes);
                    asignacionTiendasRepository.save(asignacionTiendas);
                } else {
                    AsignacionTiendas asigTiendaOld = asignacionTiendasListOld.get(0);
                    asigTiendaOld.setStock(asignacionTiendas.getStock() + asigTiendaOld.getStock());
                    asigTiendaOld.setFechaasignacion(asignacionTiendas.getFechaasignacion());
                    //asigTiendaOld.setAsignadosSedes(asignadosSedes);
                    asignacionTiendasRepository.save(asigTiendaOld);
                }
                asignadosSedes.setCantidadactual(asignadosSedes.getCantidadactual() - asignacionTiendas.getStock());
                asignadosSedesRepository.save(asignadosSedes);
                attr.addFlashAttribute("msgExito", "Producto asignado exitosamente");
                return "redirect:/sede/productosConfirmados";
            }

        }
        attr.addFlashAttribute("msgE", "El producto no existe");
        return "redirect:/sede/productosConfirmados";
    }

    @PostMapping("/confirmarRecepcion")
    public String confirmarRecepcion( AsignadosSedesId id, RedirectAttributes attr) {

        Optional<AsignadosSedes> asignadosSedesOptional = asignadosSedesRepository.findById(id);
        if (asignadosSedesOptional.isPresent()) {

            AsignadosSedesId idNew = new AsignadosSedesId(id.getGestor(), id.getSede(),
                    id.getProductoinventario(), CustomConstants.ESTADO_RECIBIDO_POR_SEDE, id.getPrecioventa());

            Optional<AsignadosSedes> asignadosSedesOldOpt = asignadosSedesRepository.findById(idNew);
            AsignadosSedes asignadosSedesNew = asignadosSedesOptional.get();

            if (asignadosSedesOldOpt.isPresent()) {

                AsignadosSedes asignadosSedesOld = asignadosSedesOldOpt.get();
                asignadosSedesOld.setStock(asignadosSedesOld.getStock() + asignadosSedesNew.getStock());
                asignadosSedesOld.setCantidadactual(asignadosSedesOld.getCantidadactual() + asignadosSedesNew.getStock());
                asignadosSedesOld.setFechaenvio(asignadosSedesNew.getFechaenvio());

                asignadosSedesRepository.save(asignadosSedesOld);
                asignadosSedesRepository.deleteById(id);

            } else {
                AsignadosSedes newAsignadosSedes = new AsignadosSedes(idNew, asignadosSedesNew);
                attr.addFlashAttribute("msgExito", "Se ha registrado la recepcion correctamente");
                asignadosSedesRepository.save(newAsignadosSedes);
                asignadosSedesRepository.deleteById(id);
            }
        }else {
            attr.addFlashAttribute("msgErrorD", "Hubo un problema, no se encontro el producto");
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

            if (mensaje==null || mensaje.isEmpty()){
                attr.addFlashAttribute("msgError", "Debe ingresar un mensaje");
            }else{
                AsignadosSedesId idNew = new AsignadosSedesId(id.getGestor(), id.getSede(),
                        id.getProductoinventario(), 3, id.getPrecioventa());
                asignadosSedesRepository.deleteById(id);
                AsignadosSedes asignadosSedes = asignadosSedesOptional.get();
                asignadosSedes.setId(idNew);
                asignadosSedes.setMensaje(mensaje);
                asignadosSedesRepository.save(asignadosSedes);
                attr.addFlashAttribute("msgExito", "Se ha reportado el problema correctamente");
            }

        }
        return "redirect:/sede/productosPorConfirmar";
    }

    @PostMapping("/devolucion")
    public String devolucionSede(@ModelAttribute("asignaciontiendas") AsignacionTiendas asignacionTiendas,
                                 @ModelAttribute("venta") Ventas ventas,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "idgestor") int idgestor,
                                 @RequestParam(value = "idsede") int idsede,
                                 @RequestParam(value = "idproductoinv") String idproductoinv,
                                 @RequestParam(value = "idestadoasign") int idestadoasign,
                                 @RequestParam(value = "idprecioventa") Float idprecioventa,
                                 HttpSession session,
                                 Model model, RedirectAttributes attr) {


        AsignadosSedesId id = new AsignadosSedesId(usuariosRepository.findById(idgestor).get(), usuariosRepository.findById(idsede).get(),
                inventarioRepository.findById(idproductoinv).get(),
                idestadoasign, idprecioventa);

        Optional<AsignadosSedes> asignadosSedesOptional = asignadosSedesRepository.findById(id);
        AsignadosSedes asignadosSedes=null;

        if (asignadosSedesOptional.isPresent()) {
            asignadosSedes = asignadosSedesOptional.get();
            if (asignadosSedes.getCantidadactual() == 0) {
                attr.addFlashAttribute("msgNoDevolucion", "No se puede devolver este producto, dado que la cantidad actual es 0.");
                return "redirect:/sede/productosConfirmados";

            } else {
                if (!bindingResult.hasErrors()) {
                    if (ventas.getCantDevol() < 0) {
                        bindingResult.rejectValue("cantDevol", "error.user", "Ingrese un numero valido");
                    } else {
                        if (ventas.getCantDevol() > asignadosSedes.getCantidadactual()) {
                            bindingResult.rejectValue("cantDevol", "error.user", "La cantidad devuelta no puede ser mayor a la cantidad actual de la sede");
                        }
                        if(ventas.getCantDevol() == 0){
                            bindingResult.rejectValue("cantDevol", "error.user", "Ingrese un numero valido");
                        }
                    }
                }
            }
        } else {
            attr.addFlashAttribute("msgNoDevolucion", "No se puede devolver este producto, dado que no existe.");
            return "redirect:/sede/productosConfirmados";
        }


        if (bindingResult.hasFieldErrors("cantDevol")) {

            Usuarios sede = (Usuarios) session.getAttribute("usuario");
            model.addAttribute("listaProductosConfirmados", asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
            model.addAttribute("listaTiendas", tiendaRepository.findAll());
            model.addAttribute("msgErrorD", "ERROR");

            model.addAttribute("idgestor", idgestor);
            model.addAttribute("idsede", idsede);
            model.addAttribute("idproductoinv", idproductoinv);
            model.addAttribute("idestadoasign", idestadoasign);
            model.addAttribute("idprecioventa", idprecioventa);
            return "sede/ListaProductosConfirmados";
        } else {


            AsignadosSedesId idNew = new AsignadosSedesId(id.getGestor(), id.getSede(),
                    id.getProductoinventario(), CustomConstants.ESTADO_DEVUELTO_POR_SEDE, id.getPrecioventa());
            Optional<AsignadosSedes> asignSedes = asignadosSedesRepository.findById(idNew);



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
    public ResponseEntity<HashMap<String, String>> getAsignsedePost(@RequestParam(value = "gestor") Integer gestor,
                                                                    @RequestParam(value = "sede") Integer sede,
                                                                    @RequestParam(value = "productoinventario") String inv,
                                                                    @RequestParam(value = "estadoasignacion") Integer estadoasignacion,
                                                                    @RequestParam(value = "precioventa") Float precioventa) {

        AsignadosSedesId asignadosSedesId = new
                AsignadosSedesId(gestor,sede,inv,estadoasignacion,precioventa);
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

    @ResponseBody
    @PostMapping(value = "/productosConfirmados/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, String>> getAsignTiendaPost(@RequestParam(value = "gestor") Integer gestor,
                                                                    @RequestParam(value = "sede") Integer sede,
                                                                    @RequestParam(value = "productoinventario") String inv,
                                                                    @RequestParam(value = "estadoasignacion") Integer estadoasignacion,
                                                                    @RequestParam(value = "precioventa") Float precioventa) {

        AsignadosSedesId asignadosSedesId = new AsignadosSedesId(gestor,sede,inv,estadoasignacion,precioventa);
        return new ResponseEntity<>(new HashMap<String, String>() {{
            asignadosSedesId.setProductoinventario(inventarioRepository.findByCodigoinventario(asignadosSedesId.getProductoinventario().getCodigoinventario()));
            asignadosSedesRepository.findAll();
            AsignadosSedes asignadosSedes = asignadosSedesRepository.findById(asignadosSedesId).orElse(null);
            put("idgestor", Integer.toString(asignadosSedesId.getGestor().getIdusuarios()));
            put("idsede", Integer.toString(asignadosSedesId.getSede().getIdusuarios()));
            put("idproductoinv", asignadosSedesId.getProductoinventario().getCodigoinventario());
            put("idestadoasign", Integer.toString(asignadosSedesId.getEstadoasignacion()));
            put("idprecioventa", Float.toString(asignadosSedesId.getPrecioventa()));
            put("cantAsign", asignadosSedes != null ? Integer.toString(asignadosSedes.getCantidadactual()) : null);

        }},
                HttpStatus.OK);
    }

}
    