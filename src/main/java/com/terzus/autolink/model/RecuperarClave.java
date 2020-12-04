/*----------------------------------------------------------
* FILE: RecuperarClave.java
* PRODUCT: autolink
*----------------------------------------------------------
* CEL - UNIDAD DE INFORMÁTICA
* ÁREA DE DESARROLLO DE SISTEMAS
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
 * @author CEL
 * <b>Created by </b>WJuarez
 * <b>for </b>autolink
 * <b>package </b>com.terzus.autolink.model
 * <b>on </b> 12-04-2020 09:10:20 AM
 * <b>Purpose</b> 
 * <p>
 *  
 * </p>
 */
@Entity
@Table(name = "recuperarclave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecuperarClave.findAll", query = "SELECT r FROM RecuperarClave r")
})
public class RecuperarClave implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableGenerator(name = "recuperarClaveCounter",
            table = "contadores",
            pkColumnName = "tabla",
            pkColumnValue = "recuperarclave",
            valueColumnName = "valor",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "recuperarClaveCounter")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Lob
    @Size(max = 65535)
    @Column(name = "usuario")
    private String usuario;
    @Lob
    @Size(max = 65535)
    @Column(name = "clave")
    private String clave;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creacion;

    public RecuperarClave() {
    }

    public RecuperarClave(Integer id) {
        this.id = id;
    }

    public RecuperarClave(Integer id, Date creacion) {
        this.id = id;
        this.creacion = creacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
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
        if (!(object instanceof RecuperarClave)) {
            return false;
        }
        RecuperarClave other = (RecuperarClave) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.terzus.autolink.model.RecuperarClave[ id=" + id + " ]";
    }

}