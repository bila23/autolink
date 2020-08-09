/*---------------------------------------------------------
* FILE: SolicitudDespService.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of Terzus
* Its unauthorized use, as any code alteration without
* authorization is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.service;

import com.bila.framework.dao.Dao;
import com.bila.framework.service.Service;
import com.terzus.autolink.dao.SolicitudDespDao;
import com.terzus.autolink.model.Solicitud;
import com.terzus.autolink.model.Proveedor;
import com.terzus.autolink.model.SolicitudDespachada;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Aug 5, 2020 10:45:18 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class SolicitudDespService extends Service<SolicitudDespachada, Integer>{
    
    @Inject private SolicitudDespDao dao;
    @Inject private SolicitudService solService;
    @Inject private ProveedorService provService;

    @Override
    public Dao<SolicitudDespachada, Integer> getDao() {
        return dao;
    }
    
    public void save(int idSol, int idProv, String user) throws Exception{
        if(idSol == 0 || idProv == 0 || user == null) return;
        
        Solicitud solModel = solService.findByKey(idSol);
        if(solModel == null) return;
        Proveedor provModel = provService.findByKey(idProv);
        if(provModel == null) return;
        
        SolicitudDespachada model = new SolicitudDespachada();
        model.setEstado("N");
        model.setUsuarioCrea(user);
        model.setFechaCrea(new Date());
        model.setProveedor(provModel);
        model.setSolicitud(solModel);
        dao.save(model);
    }
    
    public void updateEstado(int idSol, int idProv, String state) throws Exception{
        if(idSol == 0 || idProv == 0 || state == null) return;
        dao.updateEstado(idSol, idProv, state.toUpperCase());
    }
    
    public List<SolicitudDespachada> findBySolAndState(int idSol, String state) throws Exception{
        if(idSol == 0 || state == null) return null;
        return dao.findBySolAndState(idSol, state.toUpperCase());
    }
    
    public boolean existBySolWithStateN(int idSol) throws Exception{
        List<SolicitudDespachada> list = findBySolAndState(idSol, "N");
        if(list == null || list.isEmpty()) return false;
        return true;
    }
    
    public SolicitudDespachada findBySolAndProv(int idSol, int idProv) throws Exception{
        if(idSol == 0 || idProv == 0) return null;
        List<SolicitudDespachada> list = findBySolicitud(idSol);
        for(SolicitudDespachada model : list){
            if(model.getProveedor() != null)
                if(model.getProveedor().getId() == idProv)
                    return model;
        }
        return null;
    }
    
    public List<SolicitudDespachada> findBySolicitud(int idSol) throws Exception{
        if(idSol == 0) return null;
        return dao.findBySolicitud(idSol);
    }

}
