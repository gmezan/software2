package com.example.sw2.controller.gestor;


import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Roles;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.entity.Ventas;
import com.example.sw2.repository.UsuariosRepository;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gestor/sede")
public class ListaSedeGestorController {

    private final int ROL_CRUD = 3;
    //public int estaenVentas = 0;
    //public int estaenAsignados=0;

    @Autowired
    UsuariosRepository usuariosRepository;

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
                          RedirectAttributes attr, Model model) {

        if(type==1 && usuariosRepository.findById(usuarios.getIdusuarios()).isPresent()){ //if new
            bindingResult.rejectValue("idusuarios","error.user","Este dni ya existe");
        }

        if(bindingResult.hasErrors()){

            for( ObjectError e : bindingResult.getAllErrors()){
                System.out.println(e.toString());
            }
            model.addAttribute("formtype",Integer.toString(type));
            model.addAttribute("lista", usuariosRepository.findUsuariosByRoles_idroles(ROL_CRUD));
            model.addAttribute("msgYaExiste", "ERROR");
            return "gestor/sedes";
        }
        else {
            Optional<Usuarios> optionalUsuarios = usuariosRepository.findById(usuarios.getIdusuarios());
            if (optionalUsuarios.isPresent()) {
                usuarios = optionalUsuarios.get().updateFields(usuarios); // actualizar
                if (usuarios.getRoles().getIdroles()!=ROL_CRUD) return "redirect:/gestor/sede";
                attr.addFlashAttribute("msg", "Sede actualizada exitosamente");
            }
            else {
                attr.addFlashAttribute("msg", "Sede creada exitosamente");
                usuarios.setRoles(new Roles(){{setIdroles(ROL_CRUD);}});
            }
            UploadObject.uploadProfilePhoto(usuarios,multipartFile);
            usuariosRepository.save(usuarios);
            return "redirect:/gestor/sede";
        }
    }

    @GetMapping("/delete")
    public String deleteCat(Model model,
                            @RequestParam("idusuarios") int id,
                            RedirectAttributes attr) {
        Optional<Usuarios> c = usuariosRepository.findUsuariosByRoles_idrolesAndIdusuarios(ROL_CRUD,id);

        //List<Ventas> v = usuariosRepository.buscarSedesenVentas(id);

        //List<AsignadosSedes> asigsed = usuariosRepository.buscarSedesAsignadosSedes(id,id);

        /*
        if(v.isEmpty()){
        }else{
            estaenVentas=1;
            attr.addFlashAttribute("msgErrorVent", "Esta sede no se puede borrar");
        }
        if(asigsed.isEmpty()){
        }else{
            estaenAsignados=1;
            attr.addFlashAttribute("msgErrorAsig", "Esta sede no se puede borrar");
        }

        if(estaenVentas==0 & estaenAsignados==0){
            if (c.isPresent()) {
                usuariosRepository.delete(c.get());
                attr.addFlashAttribute("msg", "Sede borrada exitosamente");
            }
        }
        */

        if (c.isPresent()) {
            usuariosRepository.delete(c.get());
            attr.addFlashAttribute("msg", "Sede borrada exitosamente");
        }

        return "redirect:/gestor/sede";
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Usuarios>> getCat(@RequestParam(value = "id") int id){
        return new ResponseEntity<>(usuariosRepository.findUsuariosByRoles_idrolesAndIdusuarios(ROL_CRUD,id), HttpStatus.OK);
    }

}
