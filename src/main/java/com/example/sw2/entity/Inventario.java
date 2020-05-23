package com.example.sw2.entity;

import com.example.sw2.constantes.CustomConstants;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

@Entity
@EnableJpaAuditing
@Table(name = "Inventario")
public class Inventario implements Serializable {

    @Id
    @Column(name = "codigo_inventario")
    private String codigoinventario;
    @NotNull(message = "Ingrese el N° de pedido.")
    @Min(value = 1,message = "Ingrese un número válido.")
    @Column(nullable = false)
    private int numpedido;
    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false)
    @NotNull(message = "Seleccione una categoría.")
    private Categorias categorias;

    @ManyToOne
    @JoinColumn(name = "producto", nullable = false)
    @NotNull(message = "Seleccione un producto.")
    private Productos productos;

    @Column(name = "tamanho", nullable = false)

    @NotEmpty(message = "Seleccione un tamaño.")
    @Size(max = 1, message = "Tamaño no válido")
    private String codtamanho;

    @ManyToOne
    @JoinColumn(name = "comunidad", nullable = false)
    @NotNull(message = "Seleccione una comunidad.")
    private Comunidades comunidades;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "artesano")
    @NotNull(message = "Seleccione un artesano.")
    private Artesanos artesanos;

    @Digits(integer = 11, fraction = 0, message = "Ingrese un número entero.")
    @Positive(message = "Ingrese una cantidad válida.")
    @NotNull(message = "Ingrese una cantidad.")
    @Column(name = "cantidad_total", nullable = false)
    private int cantidadtotal;
    @Column(name = "cantidad_gestor", nullable = false)
    private int cantidadgestor;

    @NotNull(message = "Seleccione un tipo de adquisición.")
    @Range(min = 0, max = 1, message = "Seleccione un tipo de adquisición.")
    @Column(name = "tipoadquisicion", nullable = false)
    private int codAdquisicion;
    @Column(name = "fecha_inicio_consignacion")
    private LocalDate fechainicioconsignacion;

    @Range(min = 1, max = 31, message = "Ingrese un día válido.")
    private int dia;
    @Size(max = 3, message = "Mes no válido.")
    @NotBlank(message = "Seleccione un mes.")
    @Column(name = "mes", nullable = false)
    private String codmes;
    //Falta validar años
    @Column(nullable = false)
    @NotNull(message = "Seleccione un mes")
    private int anho;
    @Size(max = 45, message = "Máximo 45 caracteres.")
    private String color;
    @Column(nullable = false)
    private String foto;
    @Column(nullable = false)
    @Digits(integer = 9, fraction = 2, message = "Costo no válido.")
    @NotNull(message = "Ingrese un costo.")
    private BigDecimal costotejedor;
    @Column(nullable = false)
    @Digits(integer = 9, fraction = 2, message = "Costo no válido.")
    @NotNull(message = "Ingrese un costo.")
    private BigDecimal costomosqoy;
    @Column(nullable = false)
    @Size(max = 45, message = "Máximo 45 caracteres.")
    @NotBlank(message = "Ingrese un facilitador.")
    private String facilitador;

    @Column(name = "fecha_vencimiento_consignacion")
    private LocalDate fechavencimientoconsignacion;

    @LastModifiedDate
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechamodificacion;
    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechacreacion;

    public Inventario() {
    }

    public Inventario(String ci) {
        this.setCodigoinventario(ci);
    }

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

    public String getCodtamanho() {
        return codtamanho;
    }

    public void setCodtamanho(String codtamanho) {
        this.codtamanho = codtamanho;
    }

    public String getTamanho() {
        return CustomConstants.getTamanhos().get(this.codtamanho);
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

    public int getCodAdquisicion() {
        return codAdquisicion;
    }

    public String getTipoAdquisicion() {
        return CustomConstants.getTiposAdquisicion().get(this.codAdquisicion);
    }

    public void setCodAdquisicion(int codAdquisicion) {
        this.codAdquisicion = codAdquisicion;
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

    public String getCodmes() {
        return codmes;
    }

    public void setCodmes(String codmes) {
        this.codmes = codmes;
    }

    public String getMes() {
        return CustomConstants.getMeses().get(this.codmes);
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
