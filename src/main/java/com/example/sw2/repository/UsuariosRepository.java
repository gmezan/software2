package com.example.sw2.repository;

import com.example.sw2.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios,Integer> {

    Usuarios findByCorreo(String correo);

    Usuarios findByIdusuarios(int dni);

    List<Usuarios> findUsuariosByRoles_Nombrerol(String rol);

    @Query(value = "SELECT * FROM mosqoy.Usuarios WHERE rol=3;",nativeQuery = true)
    List<Usuarios> buscarSedes();
    List<Usuarios> findUsuariosByRoles_idroles(int rol);

    Optional<Usuarios> findUsuariosByRoles_idrolesAndIdusuarios(int rol, int idUsuario);

}
