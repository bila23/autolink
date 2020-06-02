/*---------------------------------------------------------
* FILE: RepuestoSolicitudService.java
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
import com.terzus.autolink.dao.RepuestoDao;
import com.terzus.autolink.dao.RepuestoSolicitudDao;
import com.terzus.autolink.model.Ofertaproveedor;
import com.terzus.autolink.model.Repuesto;
import com.terzus.autolink.model.Respuestoxsolicitud;
import com.terzus.autolink.vo.OfertaProveedorVO;
import com.terzus.autolink.vo.RepuestoSolicitudVO;
import com.terzus.autolink.vo.RepuestosAllSolVO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 25, 2020 11:35:39 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class RepuestoSolicitudService extends Service<Respuestoxsolicitud, Integer>{
    
    @Inject private RepuestoSolicitudDao dao;
    @Inject private RepuestoDao repDao;
    @Inject private OfertaProvService opService;

    @Override
    public Dao<Respuestoxsolicitud, Integer> getDao() {
        return dao;
    }
    
    public List<RepuestosAllSolVO> findAllRepAndOfertas(int idSol) throws Exception{
        if(idSol == 0) return null;
        List<Respuestoxsolicitud> list = dao.findBySolicitud(idSol);
        if(list == null || list.isEmpty()) return null;
        RepuestosAllSolVO vo = null;
        int i = 0, size = list.size();
        Respuestoxsolicitud model = null;
        List<RepuestosAllSolVO> lst = new ArrayList();
        List<OfertaProveedorVO> opList = null;
        for(i = 0; i<size; i++){
            model = list.get(i);
            vo = new RepuestosAllSolVO();
            PropertyUtils.copyProperties(vo, model);
            if(vo.getAplica() == null || vo.getAplica().equals("N"))
                vo.setAplica("No");
            else
                vo.setAplica("Si");
            opList = opService.findBySolAndRep(vo.getIdsolicitud(), vo.getIdrepuesto());
            vo.setOpList(opList);
            lst.add(vo);
        }
        return lst;
    }
    
    public void updateAplica(String aplica, int id) throws Exception{
        if(aplica == null || aplica.equals("") || id == 0) return;
        dao.updateAplica(aplica.toUpperCase(), id);
    }
    
    private RepuestoSolicitudVO modelToVO(Respuestoxsolicitud model, int codprv) throws Exception{
        if(model == null) return null;
        RepuestoSolicitudVO vo = new RepuestoSolicitudVO();
        PropertyUtils.copyProperties(vo, model);
        if(vo.getAplica() != null && vo.getAplica().equals("S"))
            vo.setAplicaBoolean(true);
        else
            vo.setAplicaBoolean(false);
        if(vo.getIdrepuesto() != null && vo.getIdrepuesto() > 0){
            Repuesto rep = repDao.findByKey(vo.getIdrepuesto());
            if(rep != null)
                vo.setRepuesto(rep.getNombrerepuesto());
        }
        //RECUPERO LOS DATOS DE LAS OFERTAS
        if(codprv > 0){
            Ofertaproveedor op = opService.findBySolicitudAndProveedorAndRepuesto(model.getIdsolicitud(), codprv, vo.getIdrepuesto());
            if(op == null) return vo;
            vo.setCantidad(op.getCantidad());
            vo.setGanador(op.getGanador());
            vo.setPrecio(op.getPrecio());
            vo.setTiempo(op.getTiempo());
            vo.setEstadoOferta(op.getEstado());
        }
        
        return vo;
    }
    
    private List<RepuestoSolicitudVO> modelListToVOList(List<Respuestoxsolicitud> list, int codprv) throws Exception{
        if(list == null || list.isEmpty()) return null;
        List<RepuestoSolicitudVO> lst = new ArrayList();
        for(Respuestoxsolicitud model : list){
            lst.add(modelToVO(model, codprv));
        }
        return lst;
    }
    
    public List<RepuestoSolicitudVO> findBySolicitud(int id) throws Exception{
        if(id == 0) return null;
        return modelListToVOList(dao.findBySolicitud(id), 0);
    }
    
    public List<RepuestoSolicitudVO> findAplicaBySolicitud(int id, int codprv) throws Exception{
        if(id == 0) return null;
        return modelListToVOList(dao.findAplicaBySolicitud(id), codprv);        
    }
    
    public List<RepuestoSolicitudVO> findAplicaBySolicitud(int id) throws Exception{
        if(id == 0) return null;
        return modelListToVOList(dao.findAplicaBySolicitud(id), 0);        
    }
    
    public void save(int idSol, int idRep) throws Exception{
        Respuestoxsolicitud model = new Respuestoxsolicitud();
        model.setEstado("A");
        model.setAplica("N");
        model.setIdrepuesto(idRep);
        model.setIdsolicitud(idSol);
        dao.save(model);
    }

}
