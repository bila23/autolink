/*---------------------------------------------------------
* FILE: Respuestoxsolicitud.java
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
@Table(name = "respuestoxsolicitud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Respuestoxsolicitud.findAll", query = "SELECT r FROM Respuestoxsolicitud r"),
    @NamedQuery(name = "Respuestoxsolicitud.findCantidadTotalRepuestosBySol", query = "SELECT SUM(r.cantidad) FROM Respuestoxsolicitud r WHERE r.idsolicitud = :idsolicitud AND r.aplica = 'S' "),
    @NamedQuery(name = "Respuestoxsolicitud.findCantidadTotalRepuestosBySolAndRep", query = "SELECT SUM(r.cantidad) FROM Respuestoxsolicitud r WHERE r.idsolicitud = :idsolicitud AND r.aplica = 'S' AND r.idrepuesto = :idrepuesto "),
    @NamedQuery(name = "Respuestoxsolicitud.updateAplica", query = "UPDATE Respuestoxsolicitud r SET r.aplica = :aplica WHERE r.id = :id"),
    @NamedQuery(name = "Respuestoxsolicitud.findBySolicitud", query = "SELECT r FROM Respuestoxsolicitud r WHERE r.idsolicitud = :idsolicitud ORDER BY r.id"),
    @NamedQuery(name = "Respuestoxsolicitud.findAplicaBySolicitud", query = "SELECT r FROM Respuestoxsolicitud r WHERE r.idsolicitud = :idsolicitud AND r.aplica = 'S' ORDER BY r.id"),
    @NamedQuery(name = "Respuestoxsolicitud.findBySolAndRep", query = "SELECT r FROM Respuestoxsolicitud r WHERE r.idsolicitud = :idsolicitud AND r.idrepuesto = :idrepuesto ORDER BY r.id desc"),
    @NamedQuery(name = "Respuestoxsolicitud.findBySolicitudAndAplica", query = "SELECT r FROM Respuestoxsolicitud r WHERE r.idsolicitud = :idsolicitud AND r.aplica = :aplica"),
    @NamedQuery(name = "Respuestoxsolicitud.findAplicaBySolicitud", query = "SELECT r FROM Respuestoxsolicitud r WHERE r.idsolicitud = :idsolicitud AND r.aplica = 'S' ORDER BY r.id")
})
public class Respuestoxsolicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idrepuesto")
    private Integer idrepuesto;
    @Column(name = "idsolicitud")
    private Integer idsolicitud;
    @Size(max = 100)
    @Column(name = "estado")
    private String estado;
    @Column(name = "aplica")
    @Size(max = 1)
    private String aplica;
    @Column(name = "cantidad")
    private Integer cantidad;

    public Respuestoxsolicitud() {
    }

    public Respuestoxsolicitud(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdrepuesto() {
        return idrepuesto;
    }

    public void setIdrepuesto(Integer idrepuesto) {
        this.idrepuesto = idrepuesto;
    }

    public Integer getIdsolicitud() {
        return idsolicitud;
    }

    public void setIdsolicitud(Integer idsolicitud) {
        this.idsolicitud = idsolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAplica() {
        return aplica;
    }

    public void setAplica(String aplica) {
        this.aplica = aplica;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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
        if (!(object instanceof Respuestoxsolicitud)) {
            return false;
        }
        Respuestoxsolicitud other = (Respuestoxsolicitud) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.terzus.autolink.model.Respuestoxsolicitud[ id=" + id + " ]";
    }

}
