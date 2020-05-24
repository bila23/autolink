/*---------------------------------------------------------
* FILE: Ofertaproveedor.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of CEL
* Its unauthorized use, as any code alteration without authorization 
* is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 24, 2020 12:34:44 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Entity
@Table(name = "ofertaproveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ofertaproveedor.findAll", query = "SELECT o FROM Ofertaproveedor o"),
    @NamedQuery(name = "Ofertaproveedor.findById", query = "SELECT o FROM Ofertaproveedor o WHERE o.id = :id"),
    @NamedQuery(name = "Ofertaproveedor.findByIdsolicitud", query = "SELECT o FROM Ofertaproveedor o WHERE o.idsolicitud = :idsolicitud"),
    @NamedQuery(name = "Ofertaproveedor.findByIdrepuesto", query = "SELECT o FROM Ofertaproveedor o WHERE o.idrepuesto = :idrepuesto"),
    @NamedQuery(name = "Ofertaproveedor.findByIdproveedor", query = "SELECT o FROM Ofertaproveedor o WHERE o.idproveedor = :idproveedor"),
    @NamedQuery(name = "Ofertaproveedor.findByCantidad", query = "SELECT o FROM Ofertaproveedor o WHERE o.cantidad = :cantidad"),
    @NamedQuery(name = "Ofertaproveedor.findByEstado", query = "SELECT o FROM Ofertaproveedor o WHERE o.estado = :estado"),
    @NamedQuery(name = "Ofertaproveedor.findByTiempo", query = "SELECT o FROM Ofertaproveedor o WHERE o.tiempo = :tiempo"),
    @NamedQuery(name = "Ofertaproveedor.findByPrecio", query = "SELECT o FROM Ofertaproveedor o WHERE o.precio = :precio")})
public class Ofertaproveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idsolicitud")
    private Integer idsolicitud;
    @Column(name = "idrepuesto")
    private Integer idrepuesto;
    @Column(name = "idproveedor")
    private Integer idproveedor;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Size(max = 50)
    @Column(name = "estado")
    private String estado;
    @Lob
    @Column(name = "ganador")
    private byte[] ganador;
    @Column(name = "tiempo")
    private Integer tiempo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private Double precio;

    public Ofertaproveedor() {
    }

    public Ofertaproveedor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdsolicitud() {
        return idsolicitud;
    }

    public void setIdsolicitud(Integer idsolicitud) {
        this.idsolicitud = idsolicitud;
    }

    public Integer getIdrepuesto() {
        return idrepuesto;
    }

    public void setIdrepuesto(Integer idrepuesto) {
        this.idrepuesto = idrepuesto;
    }

    public Integer getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(Integer idproveedor) {
        this.idproveedor = idproveedor;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public byte[] getGanador() {
        return ganador;
    }

    public void setGanador(byte[] ganador) {
        this.ganador = ganador;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ofertaproveedor)) {
            return false;
        }
        Ofertaproveedor other = (Ofertaproveedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.terzus.autolink.model.Ofertaproveedor[ id=" + id + " ]";
    }

}
