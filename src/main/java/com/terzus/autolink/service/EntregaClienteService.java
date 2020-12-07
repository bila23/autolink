/*---------------------------------------------------------
* FILE: EntregaClienteService.java
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
import com.terzus.autolink.dao.EntregaClienteDao;
import com.terzus.autolink.model.EntregaCliente;
import com.terzus.autolink.vo.EntregaClienteVO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Nov 21, 2020 9:16:02 AM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
@LocalBean
public class EntregaClienteService extends Service<EntregaCliente, Integer>{

    @Inject private EntregaClienteDao dao;
    @Inject private RepuestoService repuestoService;
    @Inject private ProveedorService provService;

    @Override
    public Dao<EntregaCliente, Integer> getDao() {
        return dao;
    }
    
    public List<EntregaCliente> findBySolAndEntregado(int solicitud, String entregado) throws Exception{
        if(solicitud == 0 || entregado == null) return null;
        return dao.findBySolAndEntregado(solicitud, entregado);
    }
    
    public List<EntregaCliente> findBySolicitud(int solicitud) throws Exception{
        if(solicitud == 0) return null;
        return dao.findBySolicitud(solicitud);
    }
    
    public List<EntregaClienteVO> findVOBySolicitud(int solicitud) throws Exception{
        List<EntregaCliente> list = findBySolicitud(solicitud);
        if(list == null || list.isEmpty()) return null;
        EntregaClienteVO vo = null;
        EntregaCliente model = null;
        List<EntregaClienteVO> lst = new ArrayList();
        int i = 0, size = list.size();
        for(i = 0; i<size; i++){
            vo = new EntregaClienteVO();
            model = list.get(i);
            PropertyUtils.copyProperties(vo, model);
            vo.setNameRepuesto( repuestoService.findName(vo.getRepuesto()) );
            vo.setNameProveedor( provService.findName(vo.getProveedor()) );
            if(vo.getEntregado().toLowerCase().equals("si"))
                vo.setSeEntrego(true);
            else
                vo.setSeEntrego(false);
            lst.add(vo);
        }
        return lst;
    }
}
