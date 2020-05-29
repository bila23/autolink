/*---------------------------------------------------------
* FILE: OfertaProvService.java
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
import com.terzus.autolink.dao.OfertaProvDao;
import com.terzus.autolink.dao.ProveedorDao;
import com.terzus.autolink.dao.RepuestoDao;
import com.terzus.autolink.model.Ofertaproveedor;
import com.terzus.autolink.model.Proveedor;
import com.terzus.autolink.model.Repuesto;
import com.terzus.autolink.vo.OfertaProveedorVO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 27, 2020 7:26:53 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class OfertaProvService extends Service<Ofertaproveedor, Integer> {

    @Inject private OfertaProvDao dao;
    @Inject private RepuestoDao repDao;
    @Inject private ProveedorDao provDao;

    @Override
    public Dao<Ofertaproveedor, Integer> getDao() {
        return dao;
    }
    
    public Ofertaproveedor findBySolicitudAndProveedorAndRepuesto(int idSol, int idProv, int idRep) throws Exception{
        if(idSol == 0 || idProv == 0 || idRep == 0) return null;
        return dao.findBySolicitudAndProveedorAndRepuesto(idSol, idProv, idRep);
    }
    
    public List<OfertaProveedorVO> findBySolicitud(int idSol) throws Exception{
        if(idSol == 0) return null;
        return listModelToVO(dao.findBySolicitud(idSol));
    }
    
    private OfertaProveedorVO modelToVO(Ofertaproveedor model) throws Exception{
        if(model == null) return null;
        OfertaProveedorVO vo = new OfertaProveedorVO();
        PropertyUtils.copyProperties(vo, model);
        if(vo.getIdproveedor() != null && vo.getIdproveedor() > 0){
            Proveedor prov = provDao.findByKey(vo.getIdproveedor());
            if(prov != null)
                vo.setProveedor(prov.getNombreproveedor());
        }
        if(vo.getIdrepuesto() != null && vo.getIdrepuesto() > 0){
            Repuesto rep = repDao.findByKey(vo.getIdrepuesto());
            if(rep != null)
                vo.setRepuesto(rep.getNombrerepuesto());
        }
        return vo;
    }
    
    private List<OfertaProveedorVO> listModelToVO(List<Ofertaproveedor> list) throws Exception{
        if(list == null || list.isEmpty()) return null;
        List<OfertaProveedorVO> lst = new ArrayList();
        for(Ofertaproveedor model : list)
            lst.add(modelToVO(model));
        return lst;
    }
    
    public void updateGanador(int idSol, int idProv) throws Exception{
        if(idSol == 0 || idProv == 0) return;
        dao.updateGanador(idSol, idProv);
    }
}
