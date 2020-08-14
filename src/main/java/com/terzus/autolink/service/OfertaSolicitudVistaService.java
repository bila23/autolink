/*---------------------------------------------------------
* FILE: OfertaSolicitudVistaService.java
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
import com.terzus.autolink.dao.OfertaSolicitudVistaDao;
import com.terzus.autolink.model.OfertaSolicitudVista;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Aug 11, 2020 8:57:17 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class OfertaSolicitudVistaService extends Service<OfertaSolicitudVista, Integer>{

    @Inject private OfertaSolicitudVistaDao dao;

    @Override
    public Dao<OfertaSolicitudVista, Integer> getDao() {
        return dao;
    }
    
    public OfertaSolicitudVista findBySolAndProv(int idSolicitud, int idProveedor) throws Exception{
        if(idSolicitud == 0 || idProveedor == 0) return null;
        return dao.findBySolAndProv(idSolicitud, idProveedor);
    }
    
    public boolean isViewed(int idSolicitud, int idProveedor) throws Exception{
        if(idSolicitud == 0 || idProveedor == 0) return false;
        OfertaSolicitudVista model = findBySolAndProv(idSolicitud, idProveedor);
        if(model == null) return false;
        if(model.getEstado() == null) return false;
        if(model.getEstado().equals("S")) return true;
        if(model.getEstado().equals("N")) return false;
        return false;
    }
    
    public void update(int idSolicitud, int idProveedor, String estado) throws Exception{
        if(idSolicitud == 0 || idProveedor == 0 || estado == null) return;
        OfertaSolicitudVista model = findBySolAndProv(idSolicitud, idProveedor);
        if(model == null) return;
        model.setEstado(estado.toUpperCase());
        dao.update(model);
    }
    
}
