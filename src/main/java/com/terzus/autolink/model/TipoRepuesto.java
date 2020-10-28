/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.terzus.autolink.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author josemauricioherrera
 */
@Entity
@Table(name = "tipo_repuesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoRepuesto.findAllActive", query = "SELECT t FROM TipoRepuesto t WHERE t.estado='A'"),
    @NamedQuery(name = "TipoRepuesto.findById", query = "SELECT t FROM TipoRepuesto t WHERE t.id = :id"),
    @NamedQuery(name = "TipoRepuesto.findByNombre", query = "SELECT t FROM TipoRepuesto t WHERE t.nombre = :nombre")})
public class TipoRepuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @Size(max = 200)
    @Column(name = "image")
    private String image;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "idTipoRepuesto", fetch = FetchType.LAZY)
    private List<Repuesto> repuestoList;

    public TipoRepuesto() {
    }

    public TipoRepuesto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Repuesto> getRepuestoList() {
        return repuestoList;
    }

    public void setRepuestoList(List<Repuesto> repuestoList) {
        this.repuestoList = repuestoList;
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
        if (!(object instanceof TipoRepuesto)) {
            return false;
        }
        TipoRepuesto other = (TipoRepuesto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.terzus.autolink.model.TipoRepuesto[ id=" + id + " ]";
    }
    
}
