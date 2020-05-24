package com.example.sw2.dto;

import java.time.LocalDate;

public interface DevolucionDto {

    String getNombreproducto();
    String getNombre();
    String getApellido();
    int getTelefono();
    String getCorreo();
    int getCantidad();
    byte[] getFoto();
    int getDni();
    int getEstado();
    String getCodigo();
    LocalDate getFecha();
}
