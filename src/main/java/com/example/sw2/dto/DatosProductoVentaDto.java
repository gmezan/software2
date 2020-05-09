package com.example.sw2.dto;

import java.util.Date;

public interface DatosProductoVentaDto {

    String getNombreproducto();
    String getCodigoinventario();
    String getTamañoproducto();
    String getComunidadproducto();
    String getColorproducto();
    int getStockasignadotienda();
    int getStocksede();
    byte[] getFotoproducto();
    Date getFechaasignacionproducto();
}
