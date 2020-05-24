package com.example.sw2.controller.sede;

import com.example.sw2.constantes.AsignadosSedesId;
import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.AsignadosSedesRepository;
import com.example.sw2.repository.InventarioRepository;
import com.example.sw2.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
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

    @GetMapping(value = {"/",""}) public String init(){
        return "redirect:/sede/tienda";}


    @GetMapping("productosPorConfirmar")
    public String productosPorConfirmar( HttpSession session, Model model){

        Usuarios sede = (Usuarios) session.getAttribute("usuario");

        model.addAttribute("listaProductosPorConfirmar",asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
        return "sede/ListaProductosPorConfirmar";

    }

    @GetMapping("productosConfirmados")
    public String productosConfirmados( HttpSession session, Model model){

        Usuarios sede = (Usuarios) session.getAttribute("usuario");

        model.addAttribute("listaProductosConfirmados",asignadosSedesRepository.buscarPorSede(sede.getIdusuarios()));
        return "sede/ListaProductosConfirmados";

    }

    @PostMapping("confirmarRecepcion")
    public String confirmarRecepcion(@RequestParam(value = "idgestor") int idgestor,
                                       @RequestParam(value = "idsede") int idsede,
                                       @RequestParam(value = "idproductoinv") String idproductoinv,
                                       @RequestParam(value = "idfechaenvio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate idfechaenvio){

        Optional<AsignadosSedes> asignadosSedesOptional = asignadosSedesRepository.findById( new AsignadosSedesId(usuariosRepository.findById(idgestor).get(), usuariosRepository.findById(idsede).get(),
                inventarioRepository.findById(idproductoinv).get(),
                idfechaenvio));

        if (asignadosSedesOptional!=null){
            AsignadosSedes asignadosSedes = asignadosSedesOptional.get();
            asignadosSedes.setCodEstadoAsignacion(2);
        }
            return "redirect:/sede/productosPorConfirmar";

    }

    @PostMapping("registrarProblema")
    public String registrarProblema(@RequestParam(value = "mensaje") String mensaje,
                                    @RequestParam(value = "idgestor") int idgestor,
                                    @RequestParam(value = "idsede") int idsede,
                                    @RequestParam(value = "idproductoinv") String idproductoinv,
                                    @RequestParam(value = "idfechaenvio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate idfechaenvio){

        Optional<AsignadosSedes> asignadosSedesOptional = asignadosSedesRepository.findById( new AsignadosSedesId(usuariosRepository.findById(idgestor).get(), usuariosRepository.findById(idsede).get(),
                inventarioRepository.findById(idproductoinv).get(),
                idfechaenvio));

        if (asignadosSedesOptional!=null){
            AsignadosSedes asignadosSedes = asignadosSedesOptional.get();
            asignadosSedes.setCodEstadoAsignacion(3);
            asignadosSedes.setMensaje(mensaje);
        }
        return "redirect:/sede/productosPorConfirmar";

    }

    //Web service
    @ResponseBody
    @GetMapping(value = {"/productosPorConfirmar/get","registrarProblema/get"},produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<AsignadosSedes>> getAsignsede(@RequestParam(value = "idgestor") int idgestor,
                                                                 @RequestParam(value = "idsede") int idsede,
                                                                 @RequestParam(value = "idproductoinv") String idproductoinv,
                                                                 @RequestParam(value = "idfechaenvio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate idfechaenvio){


        return new ResponseEntity<>(asignadosSedesRepository.findById(new AsignadosSedesId(usuariosRepository.findById(idgestor).get(),
                                                                        usuariosRepository.findById(idsede).get(),
                                                                        inventarioRepository.findById(idproductoinv).get(),
                                                                        idfechaenvio)), HttpStatus.OK);
    }

}
    