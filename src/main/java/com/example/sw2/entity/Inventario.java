package com.example.sw2.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Inventario")
public class Inventario {

    @Id
    @Column(name="codigo_inventario")
    private String codigoinventario;
    @Column(nullable =false)
    private int numpedido;
    @ManyToOne
    @JoinColumn(name="categoria",nullable =false)
    private Categorias categorias;
    @ManyToOne
    @JoinColumn(name="producto",nullable =false)
    private Productos productos;

    @Column(nullable =false)
    private String tamanho;

    @ManyToOne
    @JoinColumn(name="comunidad",nullable =false)
    private Comunidades comunidades;

    @ManyToOne
    @JoinColumn(name="artesano")
    private Artesanos artesanos;
    @Column(name="cantidad_total",nullable =false)
    private int cantidadtotal;
    @Column(name="cantidad_gestor",nullable =false)
    private int cantidadgestor;
    @Column(nullable =false)
    private int tipoadquisicion;
    @Column(name="fecha_inicio_consignacion")
    private LocalDate fechainicioconsignacion;
    private int dia;
    @Column(nullable =false)
    private int mes;
    @Column(nullable =false)
    private int anho;
    private String color;
    @Column(nullable =false)
    private String foto;
    @Column(nullable =false)
    private BigDecimal costotejedor;
    @Column(nullable =false)
    private BigDecimal costomosqoy;
    @Column(nullable =false)
    private String facilitador;
    @Column(name="fecha_vencimiento_consignacion")
    private LocalDate fechavencimientoconsignacion;
    @LastModifiedDate
    @Column(name="fecha_modificacion")
    private LocalDateTime fechamodificacion;
    @CreatedDate
    @Column(name="fecha_creacion",nullable =false)
    private LocalDateTime fechacreacion;



    public String getCodigoinventario() {
        return codigoinventario;
    }

    public void setCodigoinventario(String codigoinventario) {
        this.codigoinventario = codigoinventario;
    }

    public int getNumpedido() {
        return numpedido;
    }

    public void setNumpedido(int numpedido) {
        this.numpedido = numpedido;
    }

    public Categorias getCategorias() {
        return categorias;
    }

    public void setCategorias(Categorias categorias) {
        this.categorias = categorias;
    }

    public Productos getProductos() {
        return productos;
    }

    public void setProductos(Productos productos) {
        this.productos = productos;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Comunidades getComunidades() {
        return comunidades;
    }

    public void setComunidades(Comunidades comunidades) {
        this.comunidades = comunidades;
    }

    public Artesanos getArtesanos() {
        return artesanos;
    }

    public void setArtesanos(Artesanos artesanos) {
        this.artesanos = artesanos;
    }

    public int getCantidadtotal() {
        return cantidadtotal;
    }

    public void setCantidadtotal(int cantidadtotal) {
        this.cantidadtotal = cantidadtotal;
    }

    public int getCantidadgestor() {
        return cantidadgestor;
    }

    public void setCantidadgestor(int cantidadgestor) {
        this.cantidadgestor = cantidadgestor;
    }

    public int getTipoadquisicion() {
        return tipoadquisicion;
    }

    public void setTipoadquisicion(int tipoadquisicion) {
        this.tipoadquisicion = tipoadquisicion;
    }

    public LocalDate getFechainicioconsignacion() {
        return fechainicioconsignacion;
    }

    public void setFechainicioconsignacion(LocalDate fechainicioconsignacion) {
        this.fechainicioconsignacion = fechainicioconsignacion;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public BigDecimal getCostotejedor() {
        return costotejedor;
    }

    public void setCostotejedor(BigDecimal costotejedor) {
        this.costotejedor = costotejedor;
    }

    public BigDecimal getCostomosqoy() {
        return costomosqoy;
    }

    public void setCostomosqoy(BigDecimal costomosqoy) {
        this.costomosqoy = costomosqoy;
    }

    public String getFacilitador() {
        return facilitador;
    }

    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;
    }

    public LocalDate getFechavencimientoconsignacion() {
        return fechavencimientoconsignacion;
    }

    public void setFechavencimientoconsignacion(LocalDate fechavencimientoconsignacion) {
        this.fechavencimientoconsignacion = fechavencimientoconsignacion;
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
}
