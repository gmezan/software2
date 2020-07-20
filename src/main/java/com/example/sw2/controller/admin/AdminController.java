package com.example.sw2.controller.admin;

import com.example.sw2.Dao.StorageServiceDao;
import com.example.sw2.entity.StorageServiceResponse;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.InventarioRepository;
import com.example.sw2.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    StorageServiceDao storageServiceDao;
    @Autowired
    InventarioRepository inventarioRepository;


    @GetMapping(value = {"/",""})
    public String init(){
        return "redirect:/admin/perfil";
    }

    @GetMapping("/perfil")
    public String perfilAdmin(@ModelAttribute("user") Usuarios newUser,
                               Model model,
                               HttpSession session) {
        newUser = (Usuarios) session.getAttribute("usuario");
        model.addAttribute("user", newUser);
        model.addAttribute("cantGestores", usuariosRepository.cantUsuarios(2));
        model.addAttribute("cantSedes", usuariosRepository.cantUsuarios(3));
        model.addAttribute("cantInventario", inventarioRepository.cantInventario());
        return "admin/perfilAdmin";
    }

    @PostMapping("/save")
    public String editCom(@ModelAttribute("user") @Valid Usuarios newUser,
                          BindingResult bindingResult,
                          RedirectAttributes attr, Model model, HttpSession session,
                          @RequestParam("deletefoto") String[] dfstr,
                          @RequestParam(name = "photo", required = false) MultipartFile multipartFile) throws IOException {
        StorageServiceResponse s2 = null;
        Boolean df = (dfstr.length == 2);


        Optional<Usuarios> optionalUsuarios = usuariosRepository.findById(newUser.getIdusuarios());
        Usuarios userSession=(Usuarios)session.getAttribute("usuario");

        boolean valid=(userSession.getIdusuarios()==newUser.getIdusuarios())&&optionalUsuarios.isPresent();

        if (valid) {
            Usuarios usuOld = optionalUsuarios.get();
            newUser.setFoto(usuOld.getFoto());
            if (bindingResult.hasFieldErrors("nombre") || bindingResult.hasFieldErrors("apellido") || bindingResult.hasFieldErrors("telefono")) {
                model.addAttribute("msgError", "ERROR");
                return "admin/perfilGestor";
            } else {
                if (df){
                    usuOld.setFoto("https://storage-service.mosqoy-sw2.dns-cloud.net/profile/defaultProfilePicture.jpg");
                }
                if (!multipartFile.isEmpty()) {
                    s2 = storageServiceDao.store(usuOld, multipartFile);
                    if (!s2.isSuccess()) {
                        bindingResult.rejectValue("foto", "error.user", s2.getMsg());
                        model.addAttribute("msgError", "ERROR");
                        return "admin/perfilAdmin";
                    }
                }

                usuOld.setNombre(newUser.getNombre());
                usuOld.setApellido(newUser.getApellido());
                usuOld.setTelefono(newUser.getTelefono());
                attr.addFlashAttribute("msg", "Su perfil se actualizó exitosamente");
                session.setAttribute("usuario", usuOld);
                usuariosRepository.save(usuOld);


                return "redirect:/admin/perfil";
            }
        }else{
            model.addAttribute("msgError", "Fatal error de edición");
            return "admin/perfilAdmin";
        }
    }

    //Web service
    @ResponseBody
    @GetMapping(value = {"/perfil/get", "/save/get"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Usuarios>> getUsu(@RequestParam(value = "id") int id) {
        return new ResponseEntity<>(usuariosRepository.findById(id), HttpStatus.OK);
    }

}
