package com.example.sw2.controller.gestor;

import com.example.sw2.constantes.AsignadosSedesId;
import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Inventario;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.AsignadosSedesRepository;
import com.example.sw2.repository.InventarioRepository;
import com.example.sw2.repository.UsuariosRepository;
import net.bytebuddy.dynamic.loading.ClassInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/gestor/asignado")
public class ProductosAsignadosController {

    @Autowired
    AsignadosSedesRepository asignadosSedesRepository;

    @Autowired
    InventarioRepository inventarioRepository;

    @Autowired
    UsuariosRepository usuariosRepository;

    @GetMapping("")
    public String ListarProductosAsignados(Model model){
        model.addAttribute("listaAsignados",asignadosSedesRepository.findAllByOrderByFechacreacionDesc());
        return "gestor/asignadosSedes";
    }
    @PostMapping("/descartar")
    public String descartarEnvio( @RequestParam("gestor") int gestor,
                                  @RequestParam("sede") int sede,
                                  @RequestParam("inventario") String inventario,
                                  @RequestParam("estado") int estado,
                                  @RequestParam("precio") String precio,
                                  @RequestParam("stock") int stock,
                                  RedirectAttributes attributes){

        Optional<Inventario> optionalInventario = inventarioRepository.findById(inventario);
        Inventario inv = optionalInventario.get();
        inv.setCantidadgestor(inv.getCantidadgestor()+stock);
        inventarioRepository.save(inv);
        Float pre = Float.valueOf(precio);
        Optional<Usuarios> optionalUsuarios = usuariosRepository.findById(sede);
        Usuarios sedes = optionalUsuarios.get();
        Optional<Usuarios> optUsuarios = usuariosRepository.findById(gestor);
        Usuarios gest = optUsuarios.get();
        AsignadosSedesId asignadosSedesId = new AsignadosSedesId(gest,sedes,inv,estado,pre);
        asignadosSedesRepository.deleteById(asignadosSedesId);
        attributes.addFlashAttribute("msg", "Envío descartado exitosamente");
        return "redirect:/gestor/asignado";
    }
}
