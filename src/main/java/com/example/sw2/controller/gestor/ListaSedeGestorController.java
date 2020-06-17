package com.example.sw2.controller.gestor;


import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Roles;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.AsignadosSedesRepository;
import com.example.sw2.repository.NotificaRepository;
import com.example.sw2.repository.UsuariosRepository;
import com.example.sw2.utils.CustomMailService;
import com.example.sw2.utils.UploadObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/gestor/sede")
public class ListaSedeGestorController {

    private final int ROL_CRUD = 3; // rol al que se le hace el crud

    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    AsignadosSedesRepository asignadosSedesRepository;
    @Autowired
    CustomMailService customMailService;
    @Autowired
    NotificaRepository notificaRepository;

    @GetMapping(value = {""})
    public String listaSede(@ModelAttribute("sede") Usuarios usuarios, Model model){
        model.addAttribute("lista", usuariosRepository.findUsuariosByRoles_idroles(ROL_CRUD));
        return "gestor/sedes";
    }

    @PostMapping("/save")
    public String editCat(@ModelAttribute("sede") @Valid Usuarios usuarios,
                          BindingResult bindingResult,
                          @RequestParam(name = "photo", required = false) MultipartFile multipartFile,
                          @RequestParam("type") int type,
                          RedirectAttributes attr, Model model) throws IOException {

        if(usuarios.validateUser(bindingResult,type,usuariosRepository).hasErrors()){
            model.addAttribute("formtype",Integer.toString(type))
                .addAttribute("lista", usuariosRepository.findUsuariosByRoles_idroles(ROL_CRUD))
                .addAttribute("msgError", "ERROR");
            return "gestor/sedes";
        }
        else {
            String msg;
            Optional<Usuarios> optionalUsuarios = usuariosRepository.findUsuariosByRoles_idrolesAndIdusuarios(ROL_CRUD, usuarios.getIdusuarios());
            if (optionalUsuarios.isPresent() && (type==0) ) {
                usuarios = optionalUsuarios.get().updateFields(usuarios); // actualizar
                msg = "Sede actualizada exitosamente";
            }
            else if (type==1){
                try {
                    usuarios.setRoles(new Roles(ROL_CRUD));
                    customMailService.sendEmailPassword(usuarios);
                    msg = "Sede creada exitosamente";
                }
                catch (Exception e){
                    attr.addFlashAttribute("msgError", "Hubo un problema con el envío de credenciales, no se creo el usuario");
                    return "redirect:/gestor/sede";
                }
            }
            else {
                attr.addFlashAttribute("msgError", "Ocurrió un problema, no se pudo guardar");
                return "redirect:/gestor/sede";
            }
            UploadObject.uploadProfilePhoto(usuarios,multipartFile);
            usuariosRepository.save(usuarios);
            attr.addFlashAttribute("msg", msg);
            return "redirect:/gestor/sede";
        }
    }

    @GetMapping("/delete")
    public String deleteCat(Model model,
                            @RequestParam("idusuarios") int id,
                            RedirectAttributes attr) {
        Optional<Usuarios> c = usuariosRepository.findUsuariosByRoles_idrolesAndIdusuarios(ROL_CRUD,id);

        if (c.isPresent()) {
            try {
                usuariosRepository.delete_user(c.get().getIdusuarios());
                attr.addFlashAttribute("msg", "Sede borrado exitosamente");
            }
            catch (Exception ex){
                attr.addFlashAttribute("msgError", "Ocurrió un problema, nno se pudo borrar a la sede");
                ex.fillInStackTrace();
            }
        }
        else {
            attr.addFlashAttribute("msgError", "Ocurrió un problema, nno se pudo borrar a la sede");
        }

        return "redirect:/gestor/sede";
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Usuarios>> getCat(@RequestParam(value = "id") int id){
        return new ResponseEntity<>(usuariosRepository.findUsuariosByRoles_idrolesAndIdusuarios(ROL_CRUD,id), HttpStatus.OK);
    }


    @ResponseBody
    @GetMapping(value = "/has", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<List<HashMap<String,String>>>> hasItems(@RequestParam(value = "id") int id){
        return new ResponseEntity<>(new ArrayList<List<HashMap<String, String>>>() {{
                add(new ArrayList<HashMap<String,String>>() {{
                            Objects.requireNonNull(usuariosRepository.findUsuariosByRoles_idrolesAndIdusuarios(ROL_CRUD,id).orElse(null)).getVentas().forEach((i)-> {
                                add(new HashMap<String, String>() {{
                                        put("rucdni", i.getRucdni());
                                        put("cliente", i.getNombrecliente());
                                        put("vendedor", i.getVendedor().getNombre());
                                    }});});}});
                add(new ArrayList<HashMap<String,String>>() {{
                            asignadosSedesRepository.findAsignadosSedesById_Sede_idusuarios(id).forEach((a)-> {
                                add(new HashMap<String, String>() {{
                                        put("sede", a.getId().getSede().getFullname());
                                        put("stock", Integer.toString(a.getStock()));
                                        put("vendedor", Integer.toString(a.getCantidadactual()));
                                    }});});}}); }},
                HttpStatus.OK);
    }



}
