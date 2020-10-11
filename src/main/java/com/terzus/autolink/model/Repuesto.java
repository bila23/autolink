/*---------------------------------------------------------
* FILE: Repuesto.java
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
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "repuesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Repuesto.findAll", query = "SELECT r FROM Repuesto r"),
    @NamedQuery(name = "Repuesto.findActive", query = "SELECT r FROM Repuesto r WHERE r.estado = 'A' ORDER BY r.nombrerepuesto"),
    @NamedQuery(name = "Repuesto.findByTipo", query = "SELECT r FROM Repuesto r WHERE r.estado = 'A' and r.idTipoRepuesto.id = :codtipo ORDER BY r.nombrerepuesto")
})
public class Repuesto implements Serializable {

    @JoinColumn(name = "idTipoRepuesto", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoRepuesto idTipoRepuesto;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "nombrerepuesto")
    private String nombrerepuesto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "estado")
    private String estado;
    @Size(max = 50)
    @Column(name = "usuariocrea")
    private String usuariocrea;
    @Column(name = "fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;

    public Repuesto() {
    }

    public Repuesto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombrerepuesto() {
        return nombrerepuesto;
    }

    public void setNombrerepuesto(String nombrerepuesto) {
        this.nombrerepuesto = nombrerepuesto;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Repuesto)) {
            return false;
        }
        Repuesto other = (Repuesto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.terzus.autolink.model.Repuesto[ id=" + id + " ]";
    }

    public TipoRepuesto getIdTipoRepuesto() {
        return idTipoRepuesto;
    }

    public void setIdTipoRepuesto(TipoRepuesto idTipoRepuesto) {
        this.idTipoRepuesto = idTipoRepuesto;
    }

}
