/*---------------------------------------------------------
* FILE: Aseguradora.java
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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
@Table(name = "aseguradora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aseguradora.findAll", query = "SELECT a FROM Aseguradora a"),
    @NamedQuery(name = "Aseguradora.findById", query = "SELECT a FROM Aseguradora a WHERE a.id = :id"),
    @NamedQuery(name = "Aseguradora.findByNombreaseguradora", query = "SELECT a FROM Aseguradora a WHERE a.nombreaseguradora = :nombreaseguradora"),
    @NamedQuery(name = "Aseguradora.findByCargo", query = "SELECT a FROM Aseguradora a WHERE a.cargo = :cargo"),
    @NamedQuery(name = "Aseguradora.findByRazonsocial", query = "SELECT a FROM Aseguradora a WHERE a.razonsocial = :razonsocial"),
    @NamedQuery(name = "Aseguradora.findByNit", query = "SELECT a FROM Aseguradora a WHERE a.nit = :nit"),
    @NamedQuery(name = "Aseguradora.findByIva", query = "SELECT a FROM Aseguradora a WHERE a.iva = :iva"),
    @NamedQuery(name = "Aseguradora.findByUsuariocrea", query = "SELECT a FROM Aseguradora a WHERE a.usuariocrea = :usuariocrea"),
    @NamedQuery(name = "Aseguradora.findByFechacreacion", query = "SELECT a FROM Aseguradora a WHERE a.fechacreacion = :fechacreacion")})
public class Aseguradora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "nombreaseguradora")
    private String nombreaseguradora;
    @Size(max = 50)
    @Column(name = "cargo")
    private String cargo;
    @Size(max = 50)
    @Column(name = "razonsocial")
    private String razonsocial;
    @Size(max = 50)
    @Column(name = "nit")
    private String nit;
    @Size(max = 50)
    @Column(name = "iva")
    private String iva;
    @Lob
    @Column(name = "estado")
    private byte[] estado;
    @Size(max = 50)
    @Column(name = "usuariocrea")
    private String usuariocrea;
    @Column(name = "fechacreacion")
    @Temporal(TemporalType.DATE)
    private Date fechacreacion;
    @OneToMany(mappedBy = "idusuario")
    private List<Aseguradora> aseguradoraList;
    @JoinColumn(name = "idusuario", referencedColumnName = "id")
    @ManyToOne
    private Aseguradora idusuario;

    public Aseguradora() {
    }

    public Aseguradora(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreaseguradora() {
        return nombreaseguradora;
    }

    public void setNombreaseguradora(String nombreaseguradora) {
        this.nombreaseguradora = nombreaseguradora;
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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
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

    @XmlTransient
    public List<Aseguradora> getAseguradoraList() {
        return aseguradoraList;
    }

    public void setAseguradoraList(List<Aseguradora> aseguradoraList) {
        this.aseguradoraList = aseguradoraList;
    }

    public Aseguradora getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Aseguradora idusuario) {
        this.idusuario = idusuario;
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
        if (!(object instanceof Aseguradora)) {
            return false;
        }
        Aseguradora other = (Aseguradora) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.terzus.autolink.model.Aseguradora[ id=" + id + " ]";
    }

}