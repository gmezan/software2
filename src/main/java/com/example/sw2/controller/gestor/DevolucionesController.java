package com.example.sw2.controller.gestor;

import com.example.sw2.constantes.AsignadosSedesId;
import com.example.sw2.constantes.CustomConstants;
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
import javax.servlet.http.HttpSession;
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

    private int estado_devol = CustomConstants.ESTADO_DEVUELTO_POR_SEDE;
    private int estado_recibido = CustomConstants.ESTADO_RECIBIDO_POR_SEDE;

    @GetMapping(value = {"", "/"})
    public String ListaDevoluciones(@ModelAttribute("sede") Usuarios u,
                                    @RequestParam(value = "id", required = false) Integer dni,
                                    HttpSession session,
                                    Model model, Authentication auth){

        int estado = CustomConstants.ESTADO_RECIBIDO_CON_PROBLEMAS;
        Usuarios gestor = (Usuarios) session.getAttribute("usuario");
        model.addAttribute("devueltos", asignadosSedesRepository.findById_Gestor_IdusuariosAndId_Estadoasignacion(gestor.getIdusuarios(),estado));
        return "gestor/devoluciones";
    }

    @GetMapping("/confirmar")
    public String Confirmar(@RequestParam("id1") int sede_dni,
                            @RequestParam("id2") String codigo,
                            @RequestParam("id3") Float precio,
                            @RequestParam("id4") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                            HttpSession session,
                            RedirectAttributes attr) {



        //AsignadosID = gestor - sede - inventario - estado - precio
        //sede - producto - estado
        Usuarios gestor = (Usuarios) session.getAttribute("usuario");
        Usuarios sede = usuariosRepository.findByIdusuarios(sede_dni);
        Inventario inv = inventarioRepository.findByCodigoinventario(codigo);
        AsignadosSedesId devol_id = new AsignadosSedesId(gestor, sede, inv, estado_devol, precio);

        Optional<AsignadosSedes> optAsig1 = asignadosSedesRepository.findById(devol_id);

        if (optAsig1.isPresent()) {
            AsignadosSedes as_devol = optAsig1.get();

            //Aumenta la cant_gestor(inventario) y disminuye el stock(Asignados_sedes)
            asignadosSedesRepository.devol_sede_gestor(as_devol.getStock(), gestor.getIdusuarios(),
                    sede_dni,codigo,estado_recibido,precio);

            asignadosSedesRepository.deleteById(as_devol.getId());
            //sumar lo devuelto a cant_gestor del inventario
            attr.addFlashAttribute("msg","Producto devuelto exitosamente");
        }

        return "redirect:/gestor/devoluciones/";
    }


    @GetMapping("/rechazar")
    public String Rechazar(@RequestParam("dni") int sede_dni,
                            @RequestParam("codigo") String codigo,
                            @RequestParam("precio") Float precio,
                            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                            HttpSession session,
                            RedirectAttributes attr) {

        //AsignadosID = gestor - sede - inventario - estado - precio
        //sede - producto - estado
        Usuarios gestor = (Usuarios) session.getAttribute("usuario");
        Usuarios sede = usuariosRepository.findByIdusuarios(sede_dni);
        Inventario inv = inventarioRepository.findByCodigoinventario(codigo);
        AsignadosSedesId aid = new AsignadosSedesId(gestor, sede, inv, estado_devol, precio);
        Optional<AsignadosSedes> optAsig = asignadosSedesRepository.findById(aid);

        if (optAsig.isPresent()) {
            AsignadosSedes as = optAsig.get();
            asignadosSedesRepository.rechazar_devol_sede(as.getStock(), gestor.getIdusuarios(),
                    sede_dni,codigo,estado_recibido,precio);
            asignadosSedesRepository.deleteById(as.getId());
            attr.addFlashAttribute("msg","El producto ha sido rechazado ");
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
