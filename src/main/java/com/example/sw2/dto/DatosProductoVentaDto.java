package com.example.sw2.dto;

import java.util.Date;

public interface DatosProductoVentaDto {

    String getRucdni();
    String getNombrecliente();
    int getTipodocumento();
    String getNumerodocumento();
    String getLugarVenta();
    Date getFechaventa();
    int getCantidadventa();
    Float getPrecioventa();
    String getCodigoinventario();
    String getNombreproducto();
    String getColorproducto();
    String getTamanhoproducto();
    String getComunidadproducto ();
    int getStockasignadotienda();
    Date getFechaasignacionproducto();
    byte[] getFotoproducto();
    int getStocksede();
}
