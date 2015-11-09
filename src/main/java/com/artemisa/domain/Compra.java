package com.artemisa.domain;
// Generated 27-sep-2015 9:29:00 by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Compra generated by hbm2java
 */
@Entity
@Table(name="compra")
public class Compra  extends BaseEntity implements java.io.Serializable{
    
    private static final long serialVersionUID = -1007074798728429272L;

     private Proveedor proveedor;
     private Date fechaCompra;
     private double iva;
     private String numeroFactura;
     private Set<CompraRubro> compraRubros = new HashSet<CompraRubro>(0);
     private Set<CompraDetalle> compraDetalles = new HashSet<CompraDetalle>(0);

    public Compra() {
    }

	
    public Compra(Proveedor proveedor, Date fechaCompra) {
        this.proveedor = proveedor;
        this.fechaCompra = fechaCompra;
    }
    public Compra(Proveedor proveedor, Date fechaCompra, String numeroFactura, Set<CompraRubro> compraRubros, Set<CompraDetalle> compraDetalles) {
       this.proveedor = proveedor;
       this.fechaCompra = fechaCompra;
       this.numeroFactura = numeroFactura;
       this.compraRubros = compraRubros;
       this.compraDetalles = compraDetalles;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="proveedor", nullable=false)
    public Proveedor getProveedor() {
        return this.proveedor;
    }
    
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_compra", nullable=false, length=10)
    public Date getFechaCompra() {
        return this.fechaCompra;
    }
    
    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    
    @Column(name="numero_factura", length=200)
    public String getNumeroFactura() {
        return this.numeroFactura;
    }
    
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="compra")
    public Set<CompraRubro> getCompraRubros() {
        return this.compraRubros;
    }
    
    public void setCompraRubros(Set<CompraRubro> compraRubros) {
        this.compraRubros = compraRubros;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="compra")
    public Set<CompraDetalle> getCompraDetalles() {
        return this.compraDetalles;
    }
    
    public void setCompraDetalles(Set<CompraDetalle> compraDetalles) {
        this.compraDetalles = compraDetalles;
    }

    @Column(name="iva", nullable=false, precision=22, scale=0)
    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }
}

