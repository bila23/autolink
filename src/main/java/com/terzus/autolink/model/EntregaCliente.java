/*---------------------------------------------------------
* FILE: EntregaCliente.java
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
import javax.persistence.Lob;
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
 * <b>On: </b>Nov 21, 2020 9:09:11 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Entity
@Table(name = "entregaCliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntregaCliente.findAll", query = "SELECT e FROM EntregaCliente e"),
    @NamedQuery(name = "EntregaCliente.findBySol", query = "SELECT e FROM EntregaCliente e WHERE e.solicitud = :solicitud"),
    @NamedQuery(name = "EntregaCliente.findBySolAndEntregado", query = "SELECT e FROM EntregaCliente e WHERE e.solicitud = :solicitud AND LOWER(e.entregado) = :entregado")
})
public class EntregaCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name = "entregaClienteCounter",
            table = "contadores",
            pkColumnName = "tabla",
            pkColumnValue = "entregaCliente",
            valueColumnName = "valor",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "entregaClienteCounter")

    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "solicitud")
    private Integer solicitud;
    @Column(name = "proveedor")
    private Integer proveedor;
    @Column(name = "repuesto")
    private Integer repuesto;
    @Size(max = 2)
    @Column(name = "entregado")
    private String entregado;
    @Lob
    @Size(max = 65535)
    @Column(name = "userCreate")
    private String userCreate;
    @Column(name = "dateCreate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    public EntregaCliente() {
    }

    public EntregaCliente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Integer solicitud) {
        this.solicitud = solicitud;
    }

    public Integer getProveedor() {
        return proveedor;
    }

    public void setProveedor(Integer proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getRepuesto() {
        return repuesto;
    }

    public void setRepuesto(Integer repuesto) {
        this.repuesto = repuesto;
    }

    public String getEntregado() {
        return entregado;
    }

    public void setEntregado(String entregado) {
        this.entregado = entregado;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
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
        if (!(object instanceof EntregaCliente)) {
            return false;
        }
        EntregaCliente other = (EntregaCliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.terzus.autolink.model.EntregaCliente[ id=" + id + " ]";
    }

}
