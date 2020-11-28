/*---------------------------------------------------------
* FILE: EntregaClienteDao.java
* PRODUCT: autolink
*----------------------------------------------------------
* IMPORTANT NOTICE
* This program is property of Terzus
* Its unauthorized use, as any code alteration without
* authorization is prohibited
*----------------------------------------------------------
*/
package com.terzus.autolink.dao;

import com.bila.framework.dao.Dao;
import com.terzus.autolink.model.EntregaCliente;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Nov 21, 2020 9:12:43 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
@LocalBean
public class EntregaClienteDao extends Dao<EntregaCliente, Integer>{
    
    public EntregaClienteDao(){
        super(EntregaCliente.class);
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public List<EntregaCliente> findBySolicitud(int solicitud) throws Exception{
        Map<String, Object> param = new HashMap();
        param.put("solicitud", solicitud);
        return findWithNamedQuery("EntregaCliente.findBySol", param);
    }
    
    public List<EntregaCliente> findBySolAndEntregado(int solicitud, String entregado) throws Exception{
        Map<String, Object> param = new HashMap();
        param.put("solicitud", solicitud);
        param.put("entregado", entregado.toLowerCase());
        return findWithNamedQuery("EntregaCliente.findBySolAndEntregado", param);        
    }
}
