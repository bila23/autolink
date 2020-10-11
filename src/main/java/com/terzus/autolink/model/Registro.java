/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.terzus.autolink.model;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josemauricioherrera
 */
@Entity
@Table(name = "registro")
@XmlRootElement
@NamedQueries({   
    @NamedQuery(name = "Registro.findById", query = "SELECT r FROM Registro r WHERE r.id = :id"),
    @NamedQuery(name = "Registro.findByDui", query = "SELECT r FROM Registro r WHERE r.dui = :dui"),
    @NamedQuery(name = "Registro.findByNombre", query = "SELECT r FROM Registro r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Registro.findByApellido", query = "SELECT r FROM Registro r WHERE r.apellido = :apellido"),
    @NamedQuery(name = "Registro.findBySexo", query = "SELECT r FROM Registro r WHERE r.sexo = :sexo"),
    @NamedQuery(name = "Registro.findByDirecciondomicilio", query = "SELECT r FROM Registro r WHERE r.direcciondomicilio = :direcciondomicilio"),
    @NamedQuery(name = "Registro.findByEmail", query = "SELECT r FROM Registro r WHERE LOWER(r.email) = :email"),
    @NamedQuery(name = "Registro.findByNumeromovil", query = "SELECT r FROM Registro r WHERE r.numeromovil = :numeromovil"),
    @NamedQuery(name = "Registro.findByNumerofijo", query = "SELECT r FROM Registro r WHERE r.numerofijo = :numerofijo")})
public class Registro implements Serializable {

    @JoinColumn(name = "idusuario", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Usuario idusuario;

    @Column(name = "fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Column(name = "fechamodificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodificacion;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 15)
    @Column(name = "dui")
    private String dui;
    @Size(max = 25)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 25)
    @Column(name = "apellido")
    private String apellido;
    @Size(max = 1)
    @Column(name = "sexo")
    private String sexo;
    @Size(max = 300)
    @Column(name = "direcciondomicilio")
    private String direcciondomicilio;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 30)
    @Column(name = "email")
    private String email;
    @Size(max = 15)
    @Column(name = "numeromovil")
    private String numeromovil;
    @Size(max = 15)
    @Column(name = "numerofijo")
    private String numerofijo;
    @JoinColumn(name = "iddepartamento", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Departamento iddepartamento;
    @JoinColumn(name = "idmunicipio", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Municipio idmunicipio;

    public Registro() {
    }

    public Registro(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDirecciondomicilio() {
        return direcciondomicilio;
    }

    public void setDirecciondomicilio(String direcciondomicilio) {
        this.direcciondomicilio = direcciondomicilio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeromovil() {
        return numeromovil;
    }

    public void setNumeromovil(String numeromovil) {
        this.numeromovil = numeromovil;
    }

    public String getNumerofijo() {
        return numerofijo;
    }

    public void setNumerofijo(String numerofijo) {
        this.numerofijo = numerofijo;
    }

    public Departamento getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(Departamento iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public Municipio getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(Municipio idmunicipio) {
        this.idmunicipio = idmunicipio;
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
        if (!(object instanceof Registro)) {
            return false;
        }
        Registro other = (Registro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.terzus.autolink.model.Registro[ id=" + id + " ]";
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    
}
