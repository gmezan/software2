package com.example.sw2.controller.gestor;

import com.example.sw2.Dao.StorageServiceDao;
import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.entity.*;
import com.example.sw2.repository.*;
import com.example.sw2.utils.UploadObject;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.YearMonth;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

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
    public String save(@ModelAttribute("inventario") @Valid Inventario inventario,
                       BindingResult bindingResult, Model m, RedirectAttributes attributes,
                       @RequestParam("conDia") String[] conDiastr,
                       @RequestParam(value = "fechadia", required = false) String fechadia,
                       @RequestParam(value = "fechames", required = false) String fechames,
                       @RequestParam(value = "fechaexp", required = false) String fechaexp,
                       @RequestParam(value = "linea", required = false) String linea,
                       @RequestParam(name = "foto1", required = false) MultipartFile multipartFile) throws IOException {
        if (!inventario.getColor().isEmpty()) {
            if (Pattern.compile("[0-9]").matcher(inventario.getColor()).find()) {
                bindingResult.rejectValue("color", "error.user", "No ingrese valores numéricos.");
            }
            if (inventario.getColor().trim().length() == 0) {
                bindingResult.rejectValue("color", "error.user", "No ingrese espacios.");

            }
        }
        if (!inventario.getFacilitador().isEmpty()) {
            if (Pattern.compile("[0-9]").matcher(inventario.getFacilitador()).find()) {
                bindingResult.rejectValue("facilitador", "error.user", "No ingrese valores numéricos.");
            }
            if (inventario.getFacilitador().trim().length() == 0) {
                bindingResult.rejectValue("facilitador", "error.user", "Ingrese un nombre válido.");
            }
        }

        LocalDate fechadiaformat = null;
        LocalDate fechaexpformat = null;
        YearMonth fechamesformat = null;
        LocalDate today = LocalDate.now();
        Boolean conDia = conDiastr.length == 2;

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
            if (fechaexp.isEmpty()) {
                bindingResult.rejectValue("fechavencimientoconsignacion", "error.user", "Ingrese una fecha.");
            } else {
                try {
                    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_DATE;
                    fechaexpformat = LocalDate.parse(fechaexp, dateTimeFormat);
                    inventario.setFechavencimientoconsignacion(fechaexpformat);
                    if (fechadiaformat != null) {
                        if (!fechaexpformat.isAfter(fechadiaformat)) {
                            bindingResult.rejectValue("fechavencimientoconsignacion", "error.user", "Debe ser posterior a la adquisición.");
                        }
                    }
                    if (fechamesformat != null) {
                        if (!fechaexpformat.isAfter(fechamesformat.atEndOfMonth())) {
                            bindingResult.rejectValue("fechavencimientoconsignacion", "error.user", "Debe ser posterior a la adquisición.");
                        }
                    }
                } catch (Exception e) {
                    bindingResult.rejectValue("dia", "error.user", "Ingrese una fecha válida.");
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
        if (!keySetL.contains(linea)) {
            bindingResult.rejectValue("cantidadgestor", "error.user", "Seleccione una línea de la lista.");
        } else {
            m.addAttribute("linea", linea);
            m.addAttribute("listProd", productosRepository.findProductosByCodigolinea(linea));
        }

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
            // subida de FOTO
            if(!multipartFile.isEmpty()){
                StorageServiceResponse s2 = storageServiceDao.store(inventario,multipartFile);
                if (!s2.isSuccess()){
                    bindingResult.rejectValue("foto","error.user",s2.getMsg());
                    listasCamposInv(m);
                    if (inventario.getComunidades() != null) {
                        m.addAttribute("listArt", artesanosRepository.findArtesanosByComunidades_Codigo(inventario.getComunidades().getCodigo()));
                    }
                    return "gestor/inventarioGestorForm";
                }
            }
            inventario.setCodigoinventario(codInv);
            inventario.setFechacreacion(LocalDateTime.now());
            inventario.setCantidadgestor(inventario.getCantidadtotal());
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
            }*/

            inventarioRepository.save(inventario);
            attributes.addFlashAttribute("msg", "Producto registrado exitosamente! Codigo generado: " + codInv);

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

        int cantInt = 0;
        try {
            cantInt = Integer.parseInt(cant);
            if (cantInt < 0) {
                throw new Exception("");
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("cantError", "Cantidad no válida.");
            attributes.addFlashAttribute("cant", cant);
            attributes.addFlashAttribute("msgError", "ERROR DE CANTIDAD");
            return "redirect:/gestor/inventario";
        }


        Inventario inventario = opt.get();
        inventario.setCantidadtotal(cantInt + inventario.getCantidadtotal());
        inventario.setCantidadgestor(cantInt + inventario.getCantidadgestor());
        attributes.addFlashAttribute("msg", "Producto añadido exitosamente!");


        return "redirect:/gestor/inventario";
    }


    @PostMapping("/editInv")
    public String editInv(@ModelAttribute("inventario") Inventario inv,
                          BindingResult bindingResult,
                          @RequestParam(name = "fechaexp", required = false) String fechavencimiento,
                          RedirectAttributes att,
                          Model m) {
        Optional<Inventario> opt = inventarioRepository.findById(inv.getCodigoinventario());

        if (!opt.isPresent()) {
            att.addFlashAttribute("msgError", "PRODUCTO NO ENCONTRADO");
            return "redirect:/gestor/inventario";

        }
        if (!inv.getFacilitador().isEmpty()) {
            if (Pattern.compile("[0-9]").matcher(inv.getFacilitador()).find()) {
                bindingResult.rejectValue("facilitador", "error.user", "No ingrese valores numéricos.");
            }
            if (inv.getFacilitador().trim().length() == 0) {
                bindingResult.rejectValue("facilitador", "error.user", "Ingrese un nombre válido.");
            }
        }

        if (inv.getCodAdquisicion() == 1) {
            try {
                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_DATE;
                LocalDate fechavencimientoF = LocalDate.parse(fechavencimiento, dateTimeFormat);
                inv.setFechavencimientoconsignacion(fechavencimientoF);

            } catch (Exception e) {
                bindingResult.rejectValue("facilitador", "error.user", "Ingrese un nombre válido.");
                return "redirect:/gestor/inventario";

            }
        }



        if (bindingResult.hasErrors()) {
            m.addAttribute("listaInv", inventarioRepository.findAll());
            m.addAttribute("msgError", "ERROR DE EDICION");

            return "gestor/inventarioGestor";
        }else {
            Inventario invOld = opt.get();
            invOld.setCostotejedor(inv.getCostotejedor());
            invOld.setCostomosqoy(inv.getCostomosqoy());

            invOld.setFacilitador(inv.getFacilitador());
            if (inv.getCodAdquisicion() == 1) {
                invOld.setFechavencimientoconsignacion(inv.getFechavencimientoconsignacion());
            }
            inventarioRepository.save(invOld);
            att.addFlashAttribute("msg", "Producto editado exitosamente!");
        }

        return "redirect:/gestor/inventario";
    }
    @GetMapping("/delete")
    public String borrar(Model model,
                         @RequestParam("codDelete") String id,
                         RedirectAttributes attr) {
        Optional<Inventario> c = inventarioRepository.findById(id);
        if (c.isPresent()) {
            try{
                inventarioRepository.deleteById(id);
            }catch(Exception e){
                attr.addFlashAttribute("msgError","El registro seleccionado no puede ser borrado");
                return "redirect:/gestor/inventario";
            }

            attr.addFlashAttribute("msg","Registro borrado exitosamente!");
        }else{
            attr.addFlashAttribute("msgError","El registro seleccionado no existe");
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
    @GetMapping(value = "/getInv", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Inventario>> getInv(@RequestParam(value = "id") String cod) {
        return new ResponseEntity<>(inventarioRepository.findById(cod), HttpStatus.OK);
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
