/*---------------------------------------------------------
* FILE: Callcenter.java
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
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 24, 2020 12:34:45 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Entity
@Table(name = "callcenter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Callcenter.findAll", query = "SELECT c FROM Callcenter c"),
    @NamedQuery(name = "Callcenter.findById", query = "SELECT c FROM Callcenter c WHERE c.id = :id"),
    @NamedQuery(name = "Callcenter.findByNombreempresa", query = "SELECT c FROM Callcenter c WHERE c.nombreempresa = :nombreempresa"),
    @NamedQuery(name = "Callcenter.findByRazonsocial", query = "SELECT c FROM Callcenter c WHERE c.razonsocial = :razonsocial"),
    @NamedQuery(name = "Callcenter.findByCargo", query = "SELECT c FROM Callcenter c WHERE c.cargo = :cargo"),
    @NamedQuery(name = "Callcenter.findByTelefono", query = "SELECT c FROM Callcenter c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Callcenter.findByIdusuario", query = "SELECT c FROM Callcenter c WHERE c.idusuario = :idusuario"),
    @NamedQuery(name = "Callcenter.findByUsuariocrea", query = "SELECT c FROM Callcenter c WHERE c.usuariocrea = :usuariocrea"),
    @NamedQuery(name = "Callcenter.findByFechacreacion", query = "SELECT c FROM Callcenter c WHERE c.fechacreacion = :fechacreacion")})
public class Callcenter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "nombreempresa")
    private String nombreempresa;
    @Size(max = 50)
    @Column(name = "razonsocial")
    private String razonsocial;
    @Size(max = 50)
    @Column(name = "cargo")
    private String cargo;
    @Size(max = 50)
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "idusuario")
    private Integer idusuario;
    @Lob
    @Column(name = "estado")
    private byte[] estado;
    @Size(max = 50)
    @Column(name = "usuariocrea")
    private String usuariocrea;
    @Column(name = "fechacreacion")
    @Temporal(TemporalType.DATE)
    private Date fechacreacion;

    public Callcenter() {
    }

    public Callcenter(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreempresa() {
        return nombreempresa;
    }

    public void setNombreempresa(String nombreempresa) {
        this.nombreempresa = nombreempresa;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public byte[] getEstado() {
        return estado;
    }

    public void setEstado(byte[] estado) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Callcenter)) {
            return false;
        }
        Callcenter other = (Callcenter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.terzus.autolink.model.Callcenter[ id=" + id + " ]";
    }

}
