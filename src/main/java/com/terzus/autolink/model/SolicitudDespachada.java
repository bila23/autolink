/*---------------------------------------------------------
* FILE: SolicitudDespachada.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of Terzus
* Its unauthorized use, as any code alteration without
* authorization is prohibited
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Aug 5, 2020 10:35:30 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Entity
@Table(name = "solicitudDespachada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitudDespachada.findAll", query = "SELECT s FROM SolicitudDespachada s"),
    @NamedQuery(name = "SolicitudDespachada.findBySolAndState", query = "SELECT s FROM SolicitudDespachada s WHERE s.solicitud.id = :idSolicitud AND s.estado = :estado"),
    @NamedQuery(name = "SolicitudDespachada.findBySolicitud", query = "SELECT s FROM SolicitudDespachada s WHERE s.solicitud.id = :idSolicitud"),
    @NamedQuery(name = "SolicitudDespachada.findBySolAndProv", query = "SELECT s FROM SolicitudDespachada s WHERE s.solicitud.id = :idSolicitud AND s.proveedor.id = :idProveedor"),
    @NamedQuery(name = "SolicitudDespachada.updateEstado", query = "UPDATE SolicitudDespachada s SET s.estado = :estado WHERE s.proveedor.id = :idProveedor AND s.solicitud.id = :idSolicitud"),
    @NamedQuery(name = "SolicitudDespachada.findBySol", query = "SELECT s FROM SolicitudDespachada s WHERE s.solicitud.id = :idSolicitud")
})
public class SolicitudDespachada implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableGenerator(name = "solicitudDespachadaCounter",
            table = "contadores",
            pkColumnName = "tabla",
            pkColumnValue = "solicitudDespachada",
            valueColumnName = "valor",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "solicitudDespachadaCounter")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 5)
    @Column(name = "estado")
    private String estado;
    @Lob
    @Size(max = 65535)
    @Column(name = "usuario_crea")
    private String usuarioCrea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_crea")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCrea;
    @JoinColumn(name = "solicitud", referencedColumnName = "id")
    @ManyToOne
    private Solicitud solicitud;
    @JoinColumn(name = "proveedor", referencedColumnName = "id")
    @ManyToOne
    private Proveedor proveedor;

    public SolicitudDespachada() {
    }

    public SolicitudDespachada(Integer id) {
        this.id = id;
    }

    public SolicitudDespachada(Integer id, Date fechaCrea) {
        this.id = id;
        this.fechaCrea = fechaCrea;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(String usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public Date getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(Date fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
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
        if (!(object instanceof SolicitudDespachada)) {
            return false;
        }
        SolicitudDespachada other = (SolicitudDespachada) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.terzus.autolink.model.SolicitudDespachada_1[ id=" + id + " ]";
    }

}
