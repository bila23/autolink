/*---------------------------------------------------------
* FILE: SolicitudDao.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of CEL
* Its unauthorized use, as any code alteration without authorization 
* is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.dao;

import com.bila.framework.dao.Dao;
import com.terzus.autolink.model.Solicitud;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 24, 2020 7:49:25 PM
 * <b>Purpose</b> 
 * <p>
 *      DAO para manejo de solicitudes
 * </p>
 */
@Stateless
public class SolicitudDao extends Dao<Solicitud, Integer>{
    
    public SolicitudDao(){
        super(Solicitud.class);
    }
    
    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Solicitud> findDespProvByProveedorWinner(int idProv) throws Exception{
        Map<String, Object> parameters = new HashMap();
        parameters.put("idproveedor", idProv);
        return findWithNamedQuery("Solicitud.findDespProvByProveedorWinner", parameters);
    }
    
    public List<Solicitud> findByProveedorWinner(int idProv) throws Exception{
        Map<String, Object> parameters = new HashMap();
        parameters.put("idproveedor", idProv);
        return findWithNamedQuery("Solicitud.findByProveedorWinner", parameters);
    }

    public List<Solicitud> findByEstadoAndTaller(String estado, int idTaller) throws Exception{
        Map<String, Object> parameters = new HashMap();
        parameters.put("estado", estado.toUpperCase());
        parameters.put("idtaller", idTaller);
        return findWithNamedQuery("Solicitud.findByEstadoAndTaller", parameters);
    }
    
    public List<Solicitud> findByEstado(String estado) throws Exception{
        Map<String, Object> parameters = new HashMap();
        parameters.put("estado", estado.toUpperCase());
        return findWithNamedQuery("Solicitud.findByEstado", parameters);
    }
    
    public void updateEstado(int id, String state) throws Exception{
        Map<String, Object> parameters = new HashMap();
        parameters.put("id", id);
        parameters.put("estado", state);
        super.executeUpdateOrDelete("Solicitud.updateEstado", parameters);
    }
    

}
