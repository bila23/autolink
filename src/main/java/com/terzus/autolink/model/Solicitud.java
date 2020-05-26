/*---------------------------------------------------------
* FILE: Solicitud.java
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
import javax.persistence.Lob;
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
 * <b>On: </b>May 24, 2020 12:34:46 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Entity
@Table(name = "solicitud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitud.findAll", query = "SELECT s FROM Solicitud s"),
    @NamedQuery(name = "Solicitud.findByEstado", query = "SELECT s FROM Solicitud s WHERE UPPER(s.estado) = :estado ORDER BY s.id DESC"),
    @NamedQuery(name = "Solicitud.updateEstado", query = "UPDATE Solicitud s SET s.estado = :estado WHERE s.id = :id")
})
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idtaller")
    private Integer idtaller;
    @Column(name = "idaseguradora")
    private Integer idaseguradora;
    @Column(name = "idmarca")
    private Integer idmarca;
    @Column(name = "idusuario")
    private Integer idusuario;
    @Column(name = "aniocarro")
    private Integer aniocarro;
    @Size(max = 50)
    @Column(name = "tipovehiculo")
    private String tipovehiculo;
    @Size(max = 50)
    @Column(name = "placa")
    private String placa;
    @Size(max = 50)
    @Column(name = "chasis")
    private String chasis;
    @Size(max = 50)
    @Column(name = "motor")
    private String motor;
    @Size(max = 50)
    @Column(name = "poliza")
    private String poliza;
    @Size(max = 50)
    @Column(name = "siniestro")
    private String siniestro;
    @Size(max = 50)
    @Column(name = "nombreasegurado")
    private String nombreasegurado;
    @Size(max = 50)
    @Column(name = "codigosolicitud")
    private String codigosolicitud;
    @Size(max = 50)
    @Column(name = "estado")
    private String estado;
    @Size(max = 10000)
    @Column(name = "comentariostaller")
    private String comentariostaller;
    @Lob
    @Size(max = 65535)
    @Column(name = "comentariosaseguradora")
    private String comentariosaseguradora;
    @Lob
    @Size(max = 65535)
    @Column(name = "comentariosproveedores")
    private String comentariosproveedores;
    @Column(name = "fechainicio")
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    @Column(name = "fechafin")
    @Temporal(TemporalType.DATE)
    private Date fechafin;
    @OneToMany(mappedBy = "idsolicitud")
    private List<Fotoxsolicitud> fotoxsolicitudList;

    public Solicitud() {
    }

    public Solicitud(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdtaller() {
        return idtaller;
    }

    public void setIdtaller(Integer idtaller) {
        this.idtaller = idtaller;
    }

    public Integer getIdaseguradora() {
        return idaseguradora;
    }

    public void setIdaseguradora(Integer idaseguradora) {
        this.idaseguradora = idaseguradora;
    }

    public Integer getIdmarca() {
        return idmarca;
    }

    public void setIdmarca(Integer idmarca) {
        this.idmarca = idmarca;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getAniocarro() {
        return aniocarro;
    }

    public void setAniocarro(Integer aniocarro) {
        this.aniocarro = aniocarro;
    }

    public String getTipovehiculo() {
        return tipovehiculo;
    }

    public void setTipovehiculo(String tipovehiculo) {
        this.tipovehiculo = tipovehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getPoliza() {
        return poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    public String getSiniestro() {
        return siniestro;
    }

    public void setSiniestro(String siniestro) {
        this.siniestro = siniestro;
    }

    public String getNombreasegurado() {
        return nombreasegurado;
    }

    public void setNombreasegurado(String nombreasegurado) {
        this.nombreasegurado = nombreasegurado;
    }

    public String getCodigosolicitud() {
        return codigosolicitud;
    }

    public void setCodigosolicitud(String codigosolicitud) {
        this.codigosolicitud = codigosolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentariostaller() {
        return comentariostaller;
    }

    public void setComentariostaller(String comentariostaller) {
        this.comentariostaller = comentariostaller;
    }

    public String getComentariosaseguradora() {
        return comentariosaseguradora;
    }

    public void setComentariosaseguradora(String comentariosaseguradora) {
        this.comentariosaseguradora = comentariosaseguradora;
    }

    public String getComentariosproveedores() {
        return comentariosproveedores;
    }

    public void setComentariosproveedores(String comentariosproveedores) {
        this.comentariosproveedores = comentariosproveedores;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    @XmlTransient
    public List<Fotoxsolicitud> getFotoxsolicitudList() {
        return fotoxsolicitudList;
    }

    public void setFotoxsolicitudList(List<Fotoxsolicitud> fotoxsolicitudList) {
        this.fotoxsolicitudList = fotoxsolicitudList;
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
        if (!(object instanceof Solicitud)) {
            return false;
        }
        Solicitud other = (Solicitud) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.terzus.autolink.model.Solicitud[ id=" + id + " ]";
    }

}
