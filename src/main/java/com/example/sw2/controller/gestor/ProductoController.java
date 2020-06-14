package com.example.sw2.controller.gestor;


import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.entity.Productos;
import com.example.sw2.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/gestor/producto")
public class ProductoController {


    @Autowired
    ProductosRepository productosRepository;


    @GetMapping(value = {""})
    public String listar(@ModelAttribute("producto") Productos productos, Model model) {
        model.addAttribute("lista", productosRepository.findAll());
        model.addAttribute("lineas", CustomConstants.getLineas());
        return "gestor/productos";
    }

    @PostMapping("/save")
    public String editar(@ModelAttribute("producto") @Valid Productos productos,
                          BindingResult bindingResult, @RequestParam("type") int type,
                         RedirectAttributes attr, Model model) {

        if(type==1 && productosRepository.findById(productos.getCodigonom()).isPresent()){ //if new
            bindingResult.rejectValue("codigonom","error.user","Este codigo ya existe");
        }

        if (!productos.getCodigonom().isEmpty()) {
            if (Pattern.compile("[0-9]").matcher(productos.getCodigonom()).find()) {
                bindingResult.rejectValue("codigonom", "error.user", "No ingrese valores numéricos.");
            }
            if (productos.getCodigonom().trim().length() == 0) {
                bindingResult.rejectValue("codigonom", "error.user", "Ingrese código válido.");
            }
        }

        if (!productos.getNombre().isEmpty()) {
            if (Pattern.compile("[0-9]").matcher(productos.getNombre()).find()) {
                bindingResult.rejectValue("nombre", "error.user", "No ingrese valores numéricos.");
            }
            if (productos.getNombre().trim().length() == 0) {
                bindingResult.rejectValue("nombre", "error.user", "Ingrese nombre válido.");
            }
        }

        if (!productos.getCodigodesc().isEmpty()) {
            if (Pattern.compile("[0-9]").matcher(productos.getCodigodesc()).find()) {
                bindingResult.rejectValue("codigodesc", "error.user", "No ingrese valores numéricos.");
            }
            if (productos.getCodigodesc().trim().length() == 0) {
                bindingResult.rejectValue("codigodesc", "error.user", "Ingrese código válido.");
            }
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("formtype",Integer.toString(type));
            model.addAttribute("lista", productosRepository.findAll());
            model.addAttribute("msgError", "ERROR");
            model.addAttribute("lineas", CustomConstants.getLineas());
            return "gestor/productos";
        }
        else {
            Optional<Productos> optionalProductos = productosRepository.findById(productos.getCodigonom());
            if (optionalProductos.isPresent() && type==0) {
                attr.addFlashAttribute("msg", "Producto actualizado exitosamente");
            }
            else if (type==1){
                attr.addFlashAttribute("msg", "Producto creado exitosamente");
            }
            else {
                attr.addFlashAttribute("msg", "Hubo un problema");
                return "redirect:/gestor/producto";
            }
            productosRepository.save(productos);
            return "redirect:/gestor/producto";
        }
    }

    @GetMapping("/delete")
    public String borrar(Model model,
                            @RequestParam("codigonom") String id,
                            RedirectAttributes attr) {
        Optional<Productos> c = productosRepository.findById(id);
        if (c.isPresent()) {
            productosRepository.deleteById(id);
            attr.addFlashAttribute("msg","Producto borrado exitosamente");
        }
        return "redirect:/gestor/producto";
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Productos>> getCat(@RequestParam(value = "id") String id){
        return new ResponseEntity<>(productosRepository.findById(id), HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping(value = "/has", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashMap<String,String>>> hasItems(@RequestParam(value = "id") String id){
        return new ResponseEntity<>(new ArrayList<HashMap<String,String>>() {{
            Objects.requireNonNull(productosRepository.findById(id).orElse(null)).getInventario().forEach((i)->
            {
                add(new HashMap<String, String>() {
                    {
                        put("codigo", i.getCodigoinventario());
                        put("comunidad", i.getComunidades().getNombre());
                        put("categoria", i.getCategorias().getNombre());
                    }
                });
            });
        }},
                HttpStatus.OK);
    }

}
