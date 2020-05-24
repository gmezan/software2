package com.example.sw2.repository;

import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.entity.Ventas;
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

    //@Query(value = "SELECT * FROM mosqoy2.Ventas v WHERE v.vendedor=?1;",nativeQuery = true)
    //List<Ventas> buscarSedesenVentas(int idUsuario);

    //@Query(value = "SELECT * FROM mosqoy2.Asignados_sedes asigse WHERE asigse.gestor=?1;",nativeQuery = true)
    //List<AsignadosSedes> (int idUsuario);

}
