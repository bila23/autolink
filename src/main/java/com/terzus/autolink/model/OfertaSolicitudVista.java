/*---------------------------------------------------------
* FILE: OfertaSolicitudVista.java
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Aug 11, 2020 8:40:06 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Entity
@Data
@Table(name = "oferta_solicitud_vista")
@NamedQueries({
    @NamedQuery(name = "OfertaSolicitudVista.findAll", query = "SELECT o FROM OfertaSolicitudVista o"),
    @NamedQuery(name = "OfertaSolicitudVista.findBySolicitudAndProveedor", query = "SELECT o FROM OfertaSolicitudVista o WHERE o.idSolicitud = :idSolicitud AND o.idProveedor = :idProveedor")
})
public class OfertaSolicitudVista implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name = "ofertaSolicitudVistaCounter",
            table = "contadores",
            pkColumnName = "tabla",
            pkColumnValue = "oferta_solicitud_vista",
            valueColumnName = "valor",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ofertaSolicitudVistaCounter")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idSolicitud")
    private Integer idSolicitud;
    @Column(name = "idProveedor")
    private Integer idProveedor;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @Size(max = 50)
    @Column(name = "userCrea")
    private String userCrea;
    @Column(name = "feccrea")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feccrea;

}
