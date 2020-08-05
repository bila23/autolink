/*---------------------------------------------------------
* FILE: Proveedor.java
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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 24, 2020 12:34:46 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Entity
@Table(name = "proveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p ORDER BY p.nombreproveedor"),
    @NamedQuery(name = "Proveedor.findByUser", query = "SELECT p FROM Proveedor p WHERE UPPER(p.idusuario.user) = :idusuario")
})
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "nombreproveedor")
    private String nombreproveedor;
    @Size(max = 200)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 10)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 200)
    @Column(name = "cargo")
    private String cargo;
    @Size(max = 200)
    @Column(name = "razonsocial")
    private String razonsocial;
    @Size(max = 50)
    @Column(name = "cuentabancaria")
    private String cuentabancaria;
    @Size(max = 50)
    @Column(name = "nit")
    private String nit;
    @Column(name = "estado")
    private String estado;
    @Size(max = 50)
    @Column(name = "usuariocrea")
    private String usuariocrea;
    @Column(name = "fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @JoinColumn(name = "idusuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario idusuario;
    @Column(name = "porcentaje")
    private float porcentaje;

    public Proveedor() {
    }

    public Proveedor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreproveedor() {
        return nombreproveedor;
    }

    public void setNombreproveedor(String nombreproveedor) {
        this.nombreproveedor = nombreproveedor;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getCuentabancaria() {
        return cuentabancaria;
    }

    public void setCuentabancaria(String cuentabancaria) {
        this.cuentabancaria = cuentabancaria;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuariocrea() {
        return usuariocrea;
    }

    public void setUsuariocrea(String usuariocrea) {
        this.usuariocrea = usuariocrea;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
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
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.terzus.autolink.model.Proveedor[ id=" + id + " ]";
    }

}
