package com.example.sw2.controller.gestor;

import com.example.sw2.constantes.AsignadosSedesId;
import com.example.sw2.constantes.VentasId;
import com.example.sw2.entity.*;
import com.example.sw2.repository.AsignadosSedesRepository;
import com.example.sw2.repository.InventarioRepository;
import com.example.sw2.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/gestor/devoluciones")
public class DevolucionesController {

    @Autowired
    AsignadosSedesRepository asignadosSedesRepository;
    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    InventarioRepository inventarioRepository;

    @GetMapping(value = {"", "/"})
    public String ListaDevoluciones(@ModelAttribute("sede") Usuarios u,
                                    @RequestParam(value = "id", required = false) Integer dni,
                                    Model model, Authentication auth){

        Usuarios gestor = usuariosRepository.findByCorreo(auth.getName());
        model.addAttribute("devueltos", asignadosSedesRepository.DatosDevolucion(4, gestor.getIdusuarios()));
        return "gestor/devoluciones";
    }

    @GetMapping("/confirmar")
    public String Confirmar(@RequestParam("id1") int sede_dni,
                            @RequestParam("id2") String codigo,
                            @RequestParam("id3") int estado,
                            @RequestParam("id4") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                            Authentication auth,
                            RedirectAttributes attr) {

        //sede - producto - estado
        Usuarios gestor = usuariosRepository.findByCorreo(auth.getName());
        Usuarios sede = usuariosRepository.findByIdusuarios(sede_dni);
        Inventario inv = inventarioRepository.findByCodigoinventario(codigo);
        AsignadosSedesId aid = new AsignadosSedesId(gestor, sede, inv, fecha);
        Optional<AsignadosSedes> optAsig = asignadosSedesRepository.findByIdAndCodEstadoAsignacion(aid,estado);

        if (optAsig.isPresent()) {
            asignadosSedesRepository.deleteByIdAndCodEstadoAsignacion(aid,estado);
            attr.addFlashAttribute("msg","Producto devuelto exitosamente");
        }

        return "redirect:/gestor/devoluciones/";
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Usuarios>> getTienda(@RequestParam(value = "id") int id){
        return new ResponseEntity<>(usuariosRepository.findById(id), HttpStatus.OK);
    }
}
