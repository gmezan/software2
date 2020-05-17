package com.example.sw2.controller.admin;

import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.entity.Roles;
import com.example.sw2.entity.Usuarios;
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
import java.util.Optional;

@Controller
@RequestMapping("/admin/gestor")
public class GestoresController {

    private final int ROL_CRUD = 2;

    @Autowired
    UsuariosRepository usuariosRepository;

    @GetMapping(value = {""})
    public String listaSede(@ModelAttribute("gestor")  Usuarios usuarios, Model model){
        model.addAttribute("lista", usuariosRepository.findUsuariosByRoles_idroles(ROL_CRUD));
        return "admin/listaGestor";
    }


    @PostMapping("/save")
    public String editCat(@ModelAttribute("gestor") @Valid Usuarios usuarios,
                          BindingResult bindingResult,
                          @RequestParam(name = "photo", required = false) MultipartFile multipartFile,
                          RedirectAttributes attr, Model model) {
        String url;
        if(bindingResult.hasErrors()){

            for( ObjectError e : bindingResult.getAllErrors()){
                System.out.println(e.toString());
            }
            model.addAttribute("lista", usuariosRepository.findUsuariosByRoles_idroles(ROL_CRUD));
            model.addAttribute("msg", "ERROR");
            return "admin/listaGestor";
        }
        else {
            Optional<Usuarios> optionalUsuarios = usuariosRepository.findUsuariosByRoles_idrolesAndIdusuarios(ROL_CRUD,usuarios.getIdusuarios());
            if (optionalUsuarios.isPresent()) {
                Usuarios u = optionalUsuarios.get();
                u.setRawPassword(usuarios.getPassword());
                u.setNombre(usuarios.getNombre());
                u.setApellido(usuarios.getApellido());
                u.setTelefono(usuarios.getTelefono());
                u.setCorreo(usuarios.getCorreo());
                usuarios.setFoto(u.getFoto());
                usuarios = u;
                attr.addFlashAttribute("msg", "Gestor actualizado exitosamente");
            }
            else {
                attr.addFlashAttribute("msg", "Gestor creado exitosamente");
                Roles roles = new Roles(); roles.setIdroles(ROL_CRUD);
                usuarios.setRoles(roles);
            }
            if (multipartFile!=null && !multipartFile.isEmpty()){
                try {
                    //pseudo random number
                    String name = Integer.toString(usuarios.getIdusuarios()*CustomConstants.BIGNUMBER).hashCode()+Integer.toString(usuarios.getIdusuarios());
                    System.out.println(name);
                    url = UploadObject.uploadPhoto(name,
                            multipartFile, CustomConstants.PERFIL);
                    usuarios.setFoto(url);
                }
                catch (Exception ex){
                    ex.fillInStackTrace();
                }
            }

            usuariosRepository.save(usuarios);
            return "redirect:/admin/gestor";
        }
    }

    @GetMapping("/delete")
    public String deleteCat(Model model,
                            @RequestParam("idusuario") int id,
                            RedirectAttributes attr) {
        Optional<Usuarios> c = usuariosRepository.findUsuariosByRoles_idrolesAndIdusuarios(ROL_CRUD,id);
        if (c.isPresent()) {
            usuariosRepository.deleteById(id);
            attr.addFlashAttribute("msg","Gestor borrado exitosamente");
        }
        return "redirect:/admin/gestor";
    }

    //Web service
    @ResponseBody
    @GetMapping(value = "/get",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Usuarios>> getCat(@RequestParam(value = "id") int id){

        return new ResponseEntity<>(usuariosRepository.findUsuariosByRoles_idrolesAndIdusuarios(ROL_CRUD,id), HttpStatus.OK);
    }


}


