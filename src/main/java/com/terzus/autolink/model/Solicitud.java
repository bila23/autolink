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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
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
    @NamedQuery(name = "Solicitud.findByProveedorWinner", query = "SELECT s FROM Solicitud s WHERE UPPER(s.estado) = 'GOC' AND s.id IN (SELECT DISTINCT o.idsolicitud FROM Ofertaproveedor o WHERE o.idproveedor = :idproveedor AND o.ganador = 'S') ORDER BY s.id DESC"),
    @NamedQuery(name = "Solicitud.findDespProvByProveedorWinner", query = "SELECT s FROM Solicitud s WHERE UPPER(s.estado) = 'DEP' AND s.id IN (SELECT DISTINCT o.idsolicitud FROM Ofertaproveedor o WHERE o.idproveedor = :idproveedor AND o.ganador = 'S') ORDER BY s.id DESC"),
    @NamedQuery(name = "Solicitud.findByEstado", query = "SELECT s FROM Solicitud s WHERE UPPER(s.estado) = :estado ORDER BY s.id DESC"),
    @NamedQuery(name = "Solicitud.findByEstadoAndTaller", query = "SELECT s FROM Solicitud s WHERE UPPER(s.estado) = :estado AND s.idtaller = :idtaller ORDER BY s.id DESC"),
    @NamedQuery(name = "Solicitud.findByEstadoAndAseg", query = "SELECT s FROM Solicitud s WHERE UPPER(s.estado) = :estado AND s.idaseguradora = :idaseguradora ORDER BY s.id DESC"),
    @NamedQuery(name = "Solicitud.updateEstado", query = "UPDATE Solicitud s SET s.estado = :estado WHERE s.id = :id"),
    @NamedQuery(name = "Solicitud.updateCoaTOPea", query = "UPDATE Solicitud s SET s.estado = 'PEA' WHERE s.estado = 'COA' AND s.horaFinal = :horaFinal"),
    
    @NamedQuery(name = "Solicitud.updateDesiertas", query = "UPDATE Solicitud s SET s.estado = 'CPD' WHERE s.estado = 'COA' AND s.horaFinal = :horaFinal AND s.id NOT IN (SELECT o.idsolicitud FROM Ofertaproveedor o WHERE o.idsolicitud = s.id)"),
    
    @NamedQuery(name = "Solicitud.findByEstadoAndCliente", query = "SELECT s FROM Solicitud s WHERE LOWER(s.estado) = LOWER(:estado) AND LOWER(s.usuariocrea) = LOWER(:user) ORDER BY s.id DESC")
})
public class Solicitud implements Serializable {

    @Column(name = "horaFinal")
    private int horaFinal;
    @JoinColumn(name = "idTipoVehiculo", referencedColumnName = "id")
    @ManyToOne
    private TipoVehiculo idTipoVehiculo;
    @OneToMany(mappedBy = "solicitud")
    private List<SolicitudDespachada> solicitudDespachada;
 
    @Size(max = 15)
    @Column(name = "tipoversion")
    private String tipoversion;

    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name = "solCounter",
            table = "contadores",
            pkColumnName = "tabla",
            pkColumnValue = "solicitud",
            valueColumnName = "valor",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "solCounter")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idtaller")
    private Integer idtaller;
    @Column(name = "idaseguradora")
    private Integer idaseguradora;
    @Column(name = "idmarca")
    private Integer idmarca;
    @Column(name = "idmodelo")
    private Integer idmodelo;
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;
    @Column(name = "fechafin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafin;
    @OneToMany(mappedBy = "idSolicitud")
    private List<Fotoxsolicitud> fotoxsolicitudList;
    @Size(max = 50)
    @Column(name = "usrcrea")
    private String usuariocrea;
    @Column(name = "feccrea")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;

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

    public Integer getIdmodelo() {
        return idmodelo;
    }

    public void setIdmodelo(Integer idmodelo) {
        this.idmodelo = idmodelo;
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

    @XmlTransient
    public List<SolicitudDespachada> getSolicitudDespachada() {
        return solicitudDespachada;
    }

    public void setSolicitudDespachada(List<SolicitudDespachada> solicitudDespachada) {
        this.solicitudDespachada = solicitudDespachada;
    }

    public int getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(int horaFinal) {
        this.horaFinal = horaFinal;
    }

    public TipoVehiculo getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    public void setIdTipoVehiculo(TipoVehiculo idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
    }

    public String getTipoversion() {
        return tipoversion;
    }

    public void setTipoversion(String tipoversion) {
        this.tipoversion = tipoversion;
    }

}
