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
import com.terzus.autolink.model.Repuesto;
import com.terzus.autolink.model.Respuestoxsolicitud;
import com.terzus.autolink.vo.RepuestoSolicitudVO;
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

    @Override
    public Dao<Respuestoxsolicitud, Integer> getDao() {
        return dao;
    }
    
    private RepuestoSolicitudVO modelToVO(Respuestoxsolicitud model) throws Exception{
        if(model == null) return null;
        RepuestoSolicitudVO vo = new RepuestoSolicitudVO();
        PropertyUtils.copyProperties(vo, model);
        if(vo.getIdrepuesto() != null && vo.getIdrepuesto() > 0){
            Repuesto rep = repDao.findByKey(vo.getIdrepuesto());
            if(rep != null)
                vo.setRepuesto(rep.getNombrerepuesto());
        }
        return vo;
    }
    
    private List<RepuestoSolicitudVO> modelListToVOList(List<Respuestoxsolicitud> list) throws Exception{
        if(list == null || list.isEmpty()) return null;
        List<RepuestoSolicitudVO> lst = new ArrayList();
        for(Respuestoxsolicitud model : list){
            lst.add(modelToVO(model));
        }
        return lst;
    }
    
    public List<RepuestoSolicitudVO> findBySolicitud(int id) throws Exception{
        if(id == 0) return null;
        return modelListToVOList(dao.findBySolicitud(id));
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
