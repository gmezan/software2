package com.example.sw2.entity;
import com.example.sw2.constantes.AsignadosSedesId;
import com.example.sw2.constantes.CustomConstants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="Asignados_sedes")
public class AsignadosSedes implements Serializable {

    @EmbeddedId
    private AsignadosSedesId id;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private int cantidadactual;
    @Column(nullable = false)
    private BigDecimal precioventa;
    @Column(name="estadoasignacion",nullable = false)
    private int codEstadoAsignacion;
    @LastModifiedDate
    @Column(name="fecha_modificacion")
    private LocalDateTime fechamodificacion;
    @CreatedDate
    @Column(name="fecha_creacion",nullable =false)
    private LocalDateTime fechacreacion;
    private String mensaje;

    public AsignadosSedesId getId() { return id; }

    public void setId(AsignadosSedesId id) { this.id = id; }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCantidadactual() {
        return cantidadactual;
    }

    public void setCantidadactual(int cantidadactual) {
        this.cantidadactual = cantidadactual;
    }

    public BigDecimal getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(BigDecimal precioventa) {
        this.precioventa = precioventa;
    }

    public int getCodEstadoAsignacion() {
        return codEstadoAsignacion;
    }
    public String getnombreEstadoAsignacion() {
        return CustomConstants.getEstadoAsignacion().get(this.codEstadoAsignacion);
    }
    public void setCodEstadoAsignacion(int codEstadoAsignacion) {
        this.codEstadoAsignacion = codEstadoAsignacion;
    }

    public LocalDateTime getFechamodificacion() {
        return fechamodificacion;
    }

    public void setFechamodificacion(LocalDateTime fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    public LocalDateTime getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(LocalDateTime fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
