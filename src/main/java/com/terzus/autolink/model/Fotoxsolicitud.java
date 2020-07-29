/*---------------------------------------------------------
* FILE: Fotoxsolicitud.java
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
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
@Table(name = "FotoSolicitud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fotoxsolicitud.findAll", query = "SELECT f FROM Fotoxsolicitud f"),
    @NamedQuery(name = "Fotoxsolicitud.findBySolicitud", query = "SELECT f FROM Fotoxsolicitud f WHERE f.idSolicitud.id = :idSol ORDER BY f.id")
})
public class Fotoxsolicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name = "fotoSolCounter",
            table = "contadores",
            pkColumnName = "tabla",
            pkColumnValue = "FotoSolicitud",
            valueColumnName = "valor",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "fotoSolCounter")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "foto")
    private String foto;
    @JoinColumn(name = "idSolicitud", referencedColumnName = "id")
    @ManyToOne
    private Solicitud idSolicitud;

    public Fotoxsolicitud() {
    }

    public Fotoxsolicitud(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Solicitud getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Solicitud idSolicitud) {
        this.idSolicitud = idSolicitud;
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
        if (!(object instanceof Fotoxsolicitud)) {
            return false;
        }
        Fotoxsolicitud other = (Fotoxsolicitud) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.terzus.autolink.model.Fotoxsolicitud[ id=" + id + " ]";
    }

}
