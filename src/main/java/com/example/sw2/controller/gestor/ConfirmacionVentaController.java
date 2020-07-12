package com.example.sw2.controller.gestor;

import com.example.sw2.Dao.StorageServiceDao;
import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.constantes.VentasId;
import com.example.sw2.entity.StorageServiceResponse;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.UsuariosRepository;
import com.example.sw2.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/gestor/confirmacion")
public class ConfirmacionVentaController {

    @Autowired
    VentasRepository ventasRepository;

    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    StorageServiceDao storageServiceDao;


    @GetMapping(value = {""})
    public String listVen(@ModelAttribute("venta") Ventas ven,
                          Model model,
                          HttpSession session) {
        model.addAttribute("lista", ventasRepository.findVentasByConfirmado(false));
        model.addAttribute("documentos", CustomConstants.getTiposDocumento());
        return "gestor/confirmaVentas";
    }

    @PostMapping(value = "save")
    public String confirmar(@ModelAttribute("venta") Ventas v, RedirectAttributes attr,
                            @RequestParam(name = "foto1", required = false) MultipartFile multipartFile){

        StorageServiceResponse s2;

        Optional<Ventas> optV = ventasRepository.findByIdventasAndConfirmadoAndId_Tipodocumento(
                v.getIdventas(), false, v.getId().getTipodocumento()
        );

        // Faltaría validar el numero
        System.out.println(v.getIdventas());
        System.out.println(v.getId().getTipodocumento());
        System.out.println(v.getId().getNumerodocumento());

        if (optV.isPresent()){
            if (!ventasRepository.findById(v.getId()).isPresent()){
                Ventas venta = optV.get();
                venta.getId().setNumerodocumento(v.getId().getNumerodocumento());
                venta.getId().setTipodocumento(v.getId().getTipodocumento());
                venta.setConfirmado(true);

                if(multipartFile!=null && !multipartFile.isEmpty()){
                    try {
                        s2 = storageServiceDao.store(venta,multipartFile);
                    } catch (IOException e) {
                        s2 = new StorageServiceResponse("error","Error subiendo el archivo");
                    }
                    if (!s2.isSuccess()) {
                        attr.addFlashAttribute("error", s2.getMsg());
                        return "sede/asignadoTiendas";
                    }
                }

                ventasRepository.save(venta);
                attr.addFlashAttribute("msg", "La venta se ha confirmado");
            }else {
                attr.addFlashAttribute("msgError", "El número "+v.getId().getNumerodocumento() +" ya está registrado como "+v.getId().getNombreTipodocumento());
            }
        }else {
            attr.addFlashAttribute("msgError", "Hubo un error encontrando el registro de venta");
        }



        return "redirect:/gestor/confirmacion";
    }

    @PostMapping(value = "delete")
    public String cancelar(@ModelAttribute("venta") Ventas v, RedirectAttributes attr){

        Optional<Ventas> optV = ventasRepository.findByIdventasAndConfirmado(v.getIdventas(), false);

        // Faltaría validar el numero
        System.out.println(v.getIdventas());
        System.out.println(v.getId().getTipodocumento());
        System.out.println(v.getId().getNumerodocumento());

        if (optV.isPresent()){
            ventasRepository.delete(optV.get());
            attr.addFlashAttribute("msg", "La venta se ha borrado");
        }else {
            attr.addFlashAttribute("msgError", "Hubo un error encontrando el registro de venta");
        }
        return "redirect:/gestor/confirmacion";
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getCat(@RequestParam(value = "id") Integer id){
        Map<String,Object> map =  new HashMap<>();

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Optional<Ventas> optionalVentas = ventasRepository.findByIdventasAndConfirmado(id,false);
        if (optionalVentas.isPresent()){
            Ventas v = optionalVentas.get();
            httpStatus = HttpStatus.OK;
            map.put("id", new VentasId(v.getId().getTipodocumento(), ""));
            map.put("producto",v.getInventario().getCodigoinventario());
            map.put("cantidad",v.getCantidad());
            map.put("preciounitario",v.getPrecioventa());
            map.put("suma",v.getSumaParcial());
        }
        else {

            map.put("status","error");
            map.put("msg","not found");
        }

        return new ResponseEntity<>(map, httpStatus);
    }

}
