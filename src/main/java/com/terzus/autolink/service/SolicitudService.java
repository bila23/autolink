/*---------------------------------------------------------
* FILE: SolicitudService.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of CEL
* Its unauthorized use, as any code alteration without authorization 
* is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.service;

import com.bila.framework.dao.Dao;
import com.bila.framework.service.Service;
import com.terzus.autolink.dao.AseguradoraDao;
import com.terzus.autolink.dao.MarcaDao;
import com.terzus.autolink.dao.ModeloDao;
import com.terzus.autolink.dao.RepuestoDao;
import com.terzus.autolink.dao.SolicitudDao;
import com.terzus.autolink.dao.TallerDao;
import com.terzus.autolink.model.Aseguradora;
import com.terzus.autolink.model.Marca;
import com.terzus.autolink.model.Modelo;
import com.terzus.autolink.model.Repuesto;
import com.terzus.autolink.model.Solicitud;
import com.terzus.autolink.model.Taller;
import com.terzus.autolink.vo.RepuestoSolicitudVO;
import com.terzus.autolink.vo.SolicitudVO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 24, 2020 7:53:20 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class SolicitudService extends Service<Solicitud, Integer>{
    
    @Inject private SolicitudDao dao;
    @Inject private TallerDao tallerDao;
    @Inject private AseguradoraDao asegDao;
    @Inject private MarcaDao marcaDao;
    @Inject private ModeloDao modeloDao;
    @Inject private RepuestoDao repDao;
    @Inject private RepuestoSolicitudService rsService;

    @Override
    public Dao<Solicitud, Integer> getDao() {
        return dao;
    }
    
    public void updateHorasVigencia(int idSol, int horas) throws Exception{
        Solicitud model = dao.findByKey(idSol);
        if(model != null){
            Date ini = new Date();
            Date end = addHoursToDate(ini, horas);
            model.setFechainicio(ini);
            model.setFechafin(end);
            dao.update(model);
        }
    }
    
    private Date addHoursToDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
    
    public List<Repuesto> findRepuestoActive() throws Exception{
        return repDao.findActive();
    }
    
    public int save(Solicitud model, String user) throws Exception{
        model.setEstado("ING");
        model.setFechacreacion(new Date());
        Taller taller = tallerDao.findByUser(user);
        if(taller != null)
            model.setIdtaller(taller.getId());
        model.setUsuariocrea(user);
        dao.save(model);
        return model.getId();
    }
    
    public List<Aseguradora> findAsegActive() throws Exception{
        return asegDao.findActive();
    }
    
    public List<Marca> findMarcaActive() throws Exception{
        return marcaDao.findActive();
    }
    
    public List<Modelo> findActiveByMarca(Integer idMarca) throws Exception{
        return modeloDao.findActiveByMarca(idMarca);
    }
    
    private SolicitudVO modelToVO(Solicitud model, int codprv) throws Exception{
        if(model == null) return null;
        SolicitudVO vo = new SolicitudVO();
        PropertyUtils.copyProperties(vo, model);
        if(model.getIdaseguradora() != null && model.getIdaseguradora() > 0){
            Aseguradora aseg = asegDao.findByKey(model.getIdaseguradora());
            if(aseg != null)
                vo.setAseguradora(aseg.getNombreaseguradora());
        }
        if(model.getIdmarca() != null && model.getIdmarca() > 0){
            Marca marca = marcaDao.findByKey(model.getIdmarca());
            if(marca != null)
                vo.setMarca(marca.getNombremarca());
        }
        if(model.getIdtaller() != null && model.getIdtaller() > 0){
            Taller taller = tallerDao.findByKey(model.getIdtaller());
            if(taller != null)
                vo.setTaller(taller.getNombreTaller());
        }
        if(model.getIdmodelo() != null && model.getIdmodelo() > 0){
            Modelo modelo = modeloDao.findByKey(model.getIdmodelo());
            vo.setModelo(modelo.getNombremodelo());
        }
        List<RepuestoSolicitudVO> repList = rsService.findAplicaBySolicitud(vo.getId(), codprv);
        vo.setRepAplicaList(repList);
        return vo;
    }
    
    
    private List<SolicitudVO> listModelToVO(List<Solicitud> list, int codprv) throws Exception{
        if(list == null || list.isEmpty()) return null;
        List<SolicitudVO> lst = new ArrayList();
        int i = 0, size = list.size();
        for(i = 0; i<size; i++){
            lst.add(modelToVO(list.get(i), codprv));
        }
        return lst;
    }
    
    public List<SolicitudVO> findByEstado(String estado, int codprv) throws Exception{
        if(estado == null || estado.equals("")) return null;
        List<Solicitud> list = dao.findByEstado(estado);
        if(list == null || list.isEmpty()) return null;
        return listModelToVO(list, codprv);
    }
    
    public List<SolicitudVO> findByEstado(String estado) throws Exception{
        if(estado == null || estado.equals("")) return null;
        List<Solicitud> list = dao.findByEstado(estado);
        if(list == null || list.isEmpty()) return null;
        return listModelToVO(list, 0);
    }
    
    public void updateEstado(int id, String state) throws Exception{
        if(id <= 0 || state == null || state.equals("") || state.length() != 3) return;
        dao.updateEstado(id, state.toUpperCase());
    }
    
    public List<SolicitudVO> findIngresadas() throws Exception{
        return findByEstado("ING");
    }
    
    public List<SolicitudVO> findAnuladas() throws Exception{
        return findByEstado("ANU");
    }
    
    public List<SolicitudVO> findAnuladasByAdmin() throws Exception{
        return findByEstado("ANA");
    }
    
    public List<SolicitudVO> findCotAbierta() throws Exception{
        return findByEstado("COA");
    }
    
    public List<SolicitudVO> findCotAbierta(int codprv) throws Exception{
        return findByEstado("COA", codprv);
    }
    
    public List<SolicitudVO> findCerradaDesierta() throws Exception{
        return findByEstado("CPD");
    }
    
    public List<SolicitudVO> findCerradaAseg() throws Exception{
        return findByEstado("CEA");
    }
    
    public List<SolicitudVO> findCerradaParcialmente() throws Exception{
        return findByEstado("CPA");
    }
    
    public List<SolicitudVO> findPendAprobar() throws Exception{
        return findByEstado("PEA");
    }

    public List<SolicitudVO> findGenOrdCompra() throws Exception{
        return findByEstado("GOC");
    }    

    public List<SolicitudVO> findDespProveedor() throws Exception{
        return findByEstado("DEP");
    }
    
    public List<SolicitudVO> findEntSatCliente() throws Exception{
        return findByEstado("ESC");
    }

}
