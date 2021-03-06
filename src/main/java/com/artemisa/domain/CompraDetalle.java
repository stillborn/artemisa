package com.artemisa.domain;
// Generated 27-sep-2015 9:29:00 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CompraDetalle generated by hbm2java
 */
@Entity
@Table(name="compra_detalle")
public class CompraDetalle extends BaseEntity implements java.io.Serializable {
    
    private static final long serialVersionUID = 7934794395144142950L;
    
    private Boolean bonificado;
    private int cantidad;
    private Compra compra;
    private double precio;
    private Producto producto;
    
    public CompraDetalle() {
    }
    
    
    public CompraDetalle(int cantidad, Compra compra, double precio, Producto producto) {
        this.cantidad = cantidad;
        this.compra = compra;
        this.precio = precio;
        this.producto = producto;
    }
    public CompraDetalle(Boolean bonificado, int cantidad, Compra compra, double precio, Producto producto) {
        this.bonificado = bonificado;
        this.cantidad = cantidad;
        this.compra = compra;
        this.precio = precio;
        this.producto = producto;
    }
    
    @Column(name="bonificado")
    public Boolean getBonificado() {
        return this.bonificado;
    }
    
    public void setBonificado(Boolean bonificado) {
        this.bonificado = bonificado;
    }
    
    
    @Column(name="cantidad", nullable=false)
    public int getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="compra", nullable=false)
    public Compra getCompra() {
        return this.compra;
    }
    
    public void setCompra(Compra compra) {
        this.compra = compra;
    }
    
    
    @Column(name="precio", nullable=false, precision=22, scale=0)
    public double getPrecio() {
        return this.precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="producto", nullable=false)
    public Producto getProducto() {
        return this.producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}


