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
import com.terzus.autolink.model.Ofertaproveedor;
import javax.ejb.Stateless;
import javax.inject.Inject;

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

    @Override
    public Dao<Ofertaproveedor, Integer> getDao() {
        return dao;
    }
    
    public Ofertaproveedor findBySolicitudAndProveedorAndRepuesto(int idSol, int idProv, int idRep) throws Exception{
        if(idSol == 0 || idProv == 0 || idRep == 0) return null;
        return dao.findBySolicitudAndProveedorAndRepuesto(idSol, idProv, idRep);
    }
}
