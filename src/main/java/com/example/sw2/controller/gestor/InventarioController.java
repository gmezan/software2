package com.example.sw2.controller.gestor;

import com.example.sw2.Dao.StorageServiceDao;
import com.example.sw2.constantes.CustomConstants;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.YearMonth;
import java.util.Optional;
import java.util.Set;

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
    @Autowired
    StorageServiceDao storageServiceDao;

    @GetMapping(value = {"/"})
    public String red() {
        return "redirect:/gestor/inventario";
    }

    @GetMapping(value = {""})
    public String listInv(@ModelAttribute("inventario") Inventario inventario, Model m) {
        m.addAttribute("listaInv", inventarioRepository.findAllByOrderByFechamodificacionDesc());

        return "gestor/inventarioGestor";
    }

    @GetMapping(value = {"/form/"})
    public String red2() {
        return "redirect:/gestor/inventario/form";
    }

    @GetMapping(value = {"/form"})
    public String form(@ModelAttribute("inventario") Inventario inventario, Model m) {
        listasCamposInv(m);
        List<Inventario> listaordenada = inventarioRepository.findAllByOrderByNumpedidoDesc();
        int num;
        if (listaordenada.isEmpty())
            num = 0;
        else
            num = listaordenada.get(0).getNumpedido() + 1;
        inventario.setNumpedido(num);
        return "gestor/inventarioGestorForm";
    }


    @PostMapping(value = {"/save"})
    public String save(@ModelAttribute("inventario") @Valid Inventario inventario,
                       BindingResult bindingResult, Model m, RedirectAttributes attributes,
                       @RequestParam("conDia") String[] conDiastr,
                       @RequestParam(value = "fechadia", required = false) String fechadia,
                       @RequestParam(value = "fechames", required = false) String fechames,
                       @RequestParam(value = "linea", required = false) String linea,
                       @RequestParam(name = "foto1", required = false) MultipartFile multipartFile) throws IOException {

        LocalDate fechadiaformat = null;
        YearMonth fechamesformat = null;
        LocalDate today = LocalDate.now();
        Boolean conDia = (conDiastr.length == 2);
        StorageServiceResponse s2 = null;

        if (conDia) {
            if (fechadia.isEmpty()) {
                bindingResult.rejectValue("dia", "error.user", "Ingrese una fecha.");
            } else {
                try {
                    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_DATE;
                    fechadiaformat = LocalDate.parse(fechadia, dateTimeFormat);
                    inventario.setFechaDiaFORMAT(fechadiaformat);

                    if (fechadiaformat.isAfter(today)) {
                        bindingResult.rejectValue("dia", "error.user", "No puede ser después de la fecha actual.");
                    }
                } catch (Exception e) {
                    bindingResult.rejectValue("dia", "error.user", "Ingrese una fecha válida.");
                }
            }

        } else {
            if (fechames.isEmpty()) {
                bindingResult.rejectValue("dia", "error.user", "Ingrese una fecha.");
            } else {
                try {
                    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM");
                    fechamesformat = YearMonth.parse(fechames, dateTimeFormat);
                    inventario.setFechaMesFORMAT(fechamesformat);
                    if (fechamesformat.atDay(1).isAfter(today)) {
                        bindingResult.rejectValue("dia", "error.user", "No puede ser después de la fecha actual.");
                    }
                } catch (Exception e) {
                    bindingResult.rejectValue("dia", "error.user", "Ingrese una fecha válida.");
                }
            }
        }

        if (inventario.getTipoAdquisicion().equalsIgnoreCase("consignado")) {
            if (inventario.getArtesanos() == null) {
                bindingResult.rejectValue("artesanos", "error.user", "Seleccione un artesano de la lista.");
            } else {
                inventario.setComunidades(inventario.getArtesanos().getComunidades());
            }

            if (!bindingResult.hasFieldErrors("fechavencimientoconsignacion")) {
                if (inventario.getFechavencimientoconsignacion() == null) {
                    bindingResult.rejectValue("fechavencimientoconsignacion", "error.user", "Ingrese una fecha.");
                } else {
                    if (fechadiaformat != null) {
                        if (!inventario.getFechavencimientoconsignacion().isAfter(fechadiaformat)) {
                            bindingResult.rejectValue("fechavencimientoconsignacion", "error.user", "Debe ser posterior a la adquisición.");
                        }
                    }
                    if (fechamesformat != null) {
                        if (!inventario.getFechavencimientoconsignacion().isAfter(fechamesformat.atEndOfMonth())) {
                            bindingResult.rejectValue("fechavencimientoconsignacion", "error.user", "Debe ser posterior a la adquisición.");
                        }
                    }

                }
            }


        }

        Optional<Inventario> optionalInventario = inventarioRepository.findInventariosByNumpedido(inventario.getNumpedido());
        if (optionalInventario.isPresent()) {
            bindingResult.rejectValue("numpedido", "error.user", "Este número de pedido ya existe.");
        }

        Set<String> keySetT = CustomConstants.getTamanhos().keySet();
        if (!keySetT.contains(inventario.getCodtamanho())) {
            bindingResult.rejectValue("codtamanho", "error.user", "Seleccione un tamaño de la lista.");
        }

        Set<String> keySetL = CustomConstants.getLineas().keySet();
        if (!keySetL.contains(inventario.getProductos().getId().getCodigolinea())) {
            bindingResult.rejectValue("cantidadgestor", "error.user", "Seleccione una línea de la lista.");
        } else {
            m.addAttribute("linea", inventario.getProductos().getId().getCodigolinea());
            m.addAttribute("listProd", productosRepository.findProductosByIdCodigolinea(inventario.getProductos().getId().getCodigolinea()));
        }
        System.out.println(inventario.getProductos().getId().getCodigolinea());
        System.out.println(inventario.getProductos().getId().getCodigonom());

        //Gustavo lo puso :
        //System.out.println(linea);
        //inventario.getProductos().setCodigolinea(linea);
        Productos prod = productosRepository.findById(inventario.getProductos().getId()).orElse(null);
        if (prod == null) {
            bindingResult.rejectValue("productos", "error.user", "Seleccione un producto de la lista.");
        } else {
            m.addAttribute("pr", inventario.getProductos().getId().getCodigonom());
            inventario.setProductos(prod);
        }
/*
        if (multipartFile.isEmpty()) {
            bindingResult.rejectValue("foto", "error.user", "Debe subir una foto.");
        }
*/
        if (bindingResult.hasErrors()) {
            listasCamposInv(m);
            if (inventario.getComunidades() != null) {
                m.addAttribute("listArt", artesanosRepository.findArtesanosByComunidades_Codigo(inventario.getComunidades().getCodigo()));
            }
            return "gestor/inventarioGestorForm";
        } else {
            String codInv = generaCodigo(inventario);
            optionalInventario = inventarioRepository.findById(codInv);
            if (optionalInventario.isPresent()) {
                m.addAttribute("msg", "El código " + codInv + " ya existe en el inventario. Si desea aumentar la cantidad de este producto, revise el inventario."
                );

                listasCamposInv(m);
                m.addAttribute("listArt", artesanosRepository.findAll());
                m.addAttribute("listProd", productosRepository.findAll());
                return "gestor/inventarioGestorForm";
            }
/*
            // subida de FOTO
            try {
                StorageServiceResponse s2 = storageServiceDao.store(inventario, multipartFile);
                if (!s2.isSuccess()) {
                    bindingResult.rejectValue("foto", "error.user", s2.getMsg());
                    listasCamposInv(m);
                    if (inventario.getComunidades() != null) {
                        m.addAttribute("listArt", artesanosRepository.findArtesanosByComunidades_Codigo(inventario.getComunidades().getCodigo()));
                    }
                    return "gestor/inventarioGestorForm";
                }
            } catch (HttpClientErrorException e) {
                bindingResult.rejectValue("foto", "error.user", "Error en foto");
                listasCamposInv(m);
                if (inventario.getComunidades() != null) {
                    m.addAttribute("listArt", artesanosRepository.findArtesanosByComunidades_Codigo(inventario.getComunidades().getCodigo()));
                }
                return "gestor/inventarioGestorForm";
            }
*/
            inventario.setCodigoinventario(codInv);

            if (!multipartFile.isEmpty()) {
                s2 = storageServiceDao.store(inventario, multipartFile);
                if (!s2.isSuccess()) {
                    bindingResult.rejectValue("foto", "error.user", s2.getMsg());
                    listasCamposInv(m);
                    if (inventario.getComunidades() != null) {
                        m.addAttribute("listArt", artesanosRepository.findArtesanosByComunidades_Codigo(inventario.getComunidades().getCodigo()));
                    }
                    return "gestor/inventarioGestorForm";
                }
            } else {
                bindingResult.rejectValue("foto", "error.user", "Debe seleccionar una imagen");
                listasCamposInv(m);
                if (inventario.getComunidades() != null) {
                    m.addAttribute("listArt", artesanosRepository.findArtesanosByComunidades_Codigo(inventario.getComunidades().getCodigo()));
                }
                return "gestor/inventarioGestorForm";
            }

            inventario.setCantidadgestor(inventario.getCantidadtotal());
            System.out.println(inventario.getCodigoinventario());

            try {
                inventarioRepository.save(inventario);
                attributes.addFlashAttribute("msg", "Producto registrado exitosamente! Codigo generado: " + codInv);

            } catch (Exception e) {
                attributes.addFlashAttribute("msgError", "ERROR DE REGISTRO");
            }


            return "redirect:/gestor/inventario";
        }
    }

    @PostMapping("/addInv")
    public String addInv(@RequestParam("cant") String cant, @RequestParam("codinv") String codinv, RedirectAttributes attributes, Model m) {

        Optional<Inventario> opt = inventarioRepository.findById(codinv);
        if (!opt.isPresent()) {
            attributes.addFlashAttribute("msgError", "PRODUCTO NO ENCONTRADO");
            return "redirect:/gestor/inventario";

        }

        int cantInt;
        try {
            cantInt = Integer.parseInt(cant);
            if (cantInt < 1) {
                throw new Exception("");
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("cantError", "Cantidad no válida.");
            attributes.addFlashAttribute("cant", cant);
            attributes.addFlashAttribute("codinv", codinv);
            attributes.addFlashAttribute("msgError", "ERROR DE CANTIDAD");
            return "redirect:/gestor/inventario";
        }


        Inventario inventario = opt.get();
        inventario.setCantidadtotal(cantInt + inventario.getCantidadtotal());
        inventario.setCantidadgestor(cantInt + inventario.getCantidadgestor());
        if (cantInt == 1) {
            attributes.addFlashAttribute("msg", cantInt + " producto de código " + inventario.getCodigoinventario() + " añadidos exitosamente!");
        } else {
            attributes.addFlashAttribute("msg", cantInt + " productos de código " + inventario.getCodigoinventario() + " añadidos exitosamente!");
        }


        return "redirect:/gestor/inventario";
    }


    @PostMapping("/editInv")
    public String editInv(@ModelAttribute("inventario") @Valid Inventario inv,
                          BindingResult bindingResult,
                          RedirectAttributes att,
                          Model m) {
        Optional<Inventario> opt = inventarioRepository.findById(inv.getCodigoinventario());


        if (!opt.isPresent()) {
            att.addFlashAttribute("msgError", "PRODUCTO NO ENCONTRADO");

        } else {
            Inventario invOld = opt.get();
            if (inv.getCodAdquisicion() == 1) {
                if (inv.getFechavencimientoconsignacion() == null) {
                    bindingResult.rejectValue("fechavencimientoconsignacion", "error.user", "Ingrese una fecha.");
                }
                if (!bindingResult.hasFieldErrors("fechavencimientoconsignacion")) {
                    if (invOld.getDia() == 0) {
                        DateTimeFormatter mesFormat = DateTimeFormatter.ofPattern("yyyy-MM");

                        String fechames = invOld.getAnho() + "-";
                        if (invOld.getMes() < 10) {
                            fechames += "0" + invOld.getMes();
                        } else {
                            fechames += invOld.getMes();
                        }

                        YearMonth fechamesformat = YearMonth.parse(fechames, mesFormat);

                        if (fechamesformat != null) {
                            if (!inv.getFechavencimientoconsignacion().isAfter(fechamesformat.atEndOfMonth())) {
                                bindingResult.rejectValue("fechavencimientoconsignacion", "error.user", "Debe ser posterior a la adquisición (" + fechamesformat + ")");
                            }
                        }
                    } else {
                        LocalDate fechadiaformat = invOld.getFechaadquisicion();
                        if (fechadiaformat != null) {
                            if (!inv.getFechavencimientoconsignacion().isAfter(fechadiaformat)) {
                                bindingResult.rejectValue("fechavencimientoconsignacion", "error.user", "Debe ser posterior a la adquisición (" + fechadiaformat + ")");
                            }
                        }
                    }
                }
            }


            if (bindingResult.hasFieldErrors("facilitador") || bindingResult.hasFieldErrors("costomosqoy") || bindingResult.hasFieldErrors("costotejedor") || bindingResult.hasFieldErrors("fechavencimientoconsignacion")) {
                m.addAttribute("listaInv", inventarioRepository.findAllByOrderByFechamodificacionDesc());
                m.addAttribute("msgError", "ERROR DE EDICION");
                return "gestor/inventarioGestor";
            } else {

                invOld.setCostotejedor(inv.getCostotejedor());
                invOld.setCostomosqoy(inv.getCostomosqoy());

                invOld.setFacilitador(inv.getFacilitador());
                if (inv.getCodAdquisicion() == 1) {
                    invOld.setFechavencimientoconsignacion(inv.getFechavencimientoconsignacion());
                }
                inventarioRepository.save(invOld);
                att.addFlashAttribute("msg", "Producto " + invOld.getCodigoinventario() + " actualizado exitosamente!");
            }

        }
        return "redirect:/gestor/inventario";
    }


    @GetMapping("/delete")
    public String borrar(Model model,
                         @RequestParam("codDelete") String id,
                         RedirectAttributes attr) {
        Optional<Inventario> c = inventarioRepository.findById(id);
        if (c.isPresent()) {
            try {
                inventarioRepository.deleteById(id);
            } catch (Exception e) {
                attr.addFlashAttribute("msgError", "El registro seleccionado no puede ser borrado");
                return "redirect:/gestor/inventario";
            }

            attr.addFlashAttribute("msg", "Registro borrado exitosamente!");
        } else {
            attr.addFlashAttribute("msgError", "El registro seleccionado no existe");
        }
        return "redirect:/gestor/inventario";
    }


    private void listasCamposInv(Model m) {
        LocalDate todayd = LocalDate.now();
        YearMonth todaym = YearMonth.now();
        m.addAttribute("taman", CustomConstants.getTamanhos());
        m.addAttribute("tipoAdqui", CustomConstants.getTiposAdquisicion());
        m.addAttribute("lineas", CustomConstants.getLineas());
        m.addAttribute("listCat", categoriasRepository.findAll());
        m.addAttribute("listCom", comunidadesRepository.findAll());
        m.addAttribute("todayd", todayd);
        m.addAttribute("todaym", todaym);
    }

    private String generaCodigo(Inventario inv) {
        String cod = inv.getProductos().getCodigolinea()
                + inv.getCategorias().getCodigo()
                + inv.getProductos().getCodigonom()
                + inv.getProductos().getCodigodesc()
                + inv.getCodtamanho()
                + inv.getComunidades().getCodigo();
        if (inv.getCodAdquisicion() == 1) {
            int anho = inv.getAnho() % 100;
            String mes = CustomConstants.getMeses().get(inv.getMes());

            cod += inv.getArtesanos().getCodigo()
                    + anho
                    + mes;
        }
        return cod;
    }

    //Web service
    @ResponseBody
    @GetMapping(value = {"/getInv", "/editInv/getInv", "/addInv/getInv"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Inventario>> getInv(@RequestParam(value = "id") String cod) {
        return new ResponseEntity<>(inventarioRepository.findById(cod), HttpStatus.OK);
    }

    //Web service
    @ResponseBody
    @GetMapping(value = {"/form/getLinea", "/save/getLinea"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Productos>> getCom(@RequestParam(value = "linea") String linea) {
        return new ResponseEntity<>(productosRepository.findProductosByIdCodigolinea(linea), HttpStatus.OK);
    }

    //Web service
    @ResponseBody
    @GetMapping(value = {"/form/getArtesanos", "/save/getArtesanos"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artesanos>> getArtesanos(@RequestParam(value = "comunidad") String comunidad) {
        return new ResponseEntity<>(artesanosRepository.findArtesanosByComunidades_Codigo(comunidad), HttpStatus.OK);
    }


}
