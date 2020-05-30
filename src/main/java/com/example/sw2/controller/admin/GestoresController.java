package com.example.sw2.controller.admin;

import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.entity.Roles;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.AsignadosSedesRepository;
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
import java.util.*;

@Controller
@RequestMapping("/admin/gestor")
public class GestoresController {

    private final int ROL_CRUD = 2;

    @Autowired
    UsuariosRepository usuariosRepository;

    @Autowired
    AsignadosSedesRepository asignadosSedesRepository;

    @GetMapping(value = {""})
    public String listaSede(@ModelAttribute("gestor")  Usuarios usuarios, Model model){
        model.addAttribute("lista", usuariosRepository.findUsuariosByRoles_idroles(ROL_CRUD));
        return "admin/listaGestor";
    }


    @PostMapping("/save")
    public String editCat(@ModelAttribute("gestor") @Valid Usuarios usuarios,
                          BindingResult bindingResult,
                          @RequestParam(name = "photo", required = false) MultipartFile multipartFile,
                          @RequestParam("type") int type,
                          RedirectAttributes attr, Model model) {
        String url;

        if(type==1 && usuariosRepository.findById(usuarios.getIdusuarios()).isPresent()){ //if new
            bindingResult.rejectValue("idusuarios","error.user","Este dni ya existe");
        }

        if(bindingResult.hasErrors()){

            for( ObjectError e : bindingResult.getAllErrors()){
                System.out.println(e.toString());
            }
            model.addAttribute("formtype",Integer.toString(type));
            model.addAttribute("lista", usuariosRepository.findUsuariosByRoles_idroles(ROL_CRUD));
            model.addAttribute("msg", "ERROR");
            return "admin/listaGestor";
        }
        else {
            Optional<Usuarios> optionalUsuarios = usuariosRepository.findUsuariosByRoles_idrolesAndIdusuarios(ROL_CRUD,usuarios.getIdusuarios());
            if (optionalUsuarios.isPresent()) {
                Usuarios u = optionalUsuarios.get();
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
                            @RequestParam("idusuarios") int id,
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

    @ResponseBody
    @GetMapping(value = "/has", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<List<HashMap<String,String>>>> hasItems(@RequestParam(value = "id") int id){
        return new ResponseEntity<>(new ArrayList<List<HashMap<String, String>>>() {
            {
                add(
                        new ArrayList<HashMap<String,String>>() {{
                            Objects.requireNonNull(usuariosRepository.findUsuariosByRoles_idrolesAndIdusuarios(id,ROL_CRUD).orElse(null)).getVentas().forEach((i)->
                            {
                                add(new HashMap<String, String>() {
                                    {
                                        put("rucdni", i.getRucdni());
                                        put("cliente", i.getNombrecliente());
                                        put("vendedor", i.getVendedor().getNombre());
                                    }
                                });
                            });
                        }}
                );
                add(
                        new ArrayList<HashMap<String,String>>() {{
                            asignadosSedesRepository.findAsignadosSedesById_Sede_idusuarios(id).forEach((a)->
                            {
                                add(new HashMap<String, String>() {
                                    {
                                        put("sede", a.getId().getSede().getFullname());
                                        put("stock", Integer.toString(a.getStock()));
                                        put("vendedor", Integer.toString(a.getCantidadactual()));
                                    }
                                });
                            });

                        }}
                );
            }
        },
                HttpStatus.OK);
    }


}


