/*---------------------------------------------------------
* FILE: ProveedorService.java
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
import com.terzus.autolink.dao.ProveedorDao;
import com.terzus.autolink.model.Proveedor;
import com.terzus.autolink.model.Usuario;
import com.terzus.autolink.vo.ProveedorVO;
import com.terzus.autolink.vo.SolicitudVO;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 28, 2020 6:54:07 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class ProveedorService extends Service<Proveedor, Integer>{
    
    @Inject private ProveedorDao dao;
    @Inject private UsuarioService userService;
    @Inject private SolicitudDespService solDespService;
    @Inject private SolicitudService solService;

    @Override
    public Dao<Proveedor, Integer> getDao() {
        return dao;
    }
    
    public Proveedor findByUser(String user) throws Exception{
        if(user == null || user.equals("")) return null;
        return dao.findByUser(user);
    }
    
    public int findIdProvByUser(String user) throws Exception{
        Proveedor model = findByUser(user);
        if(model == null) return 0;
        return model.getId();
    }
    
    public void saveVO(ProveedorVO vo) throws Exception{
        if(vo == null) return;
        
        //RECUPERO EL USUARIO
        Usuario userModel = userService.findByKey(vo.getIdUsuario());
        //SI EL USUARIO ES NULO NO GUARDO EL PROVEEDOR
        if(userModel == null) return;
        
        Proveedor model = new Proveedor();
        model.setEstado("A");
        PropertyUtils.copyProperties(model, vo);
        vo.setFechacreacion(new Date());
        model.setIdusuario(userModel);
        dao.save(model);
    }
    
    public List<Usuario> findUserList() throws Exception{
        return userService.findUserProveedor();
    }
    
    public void update(ProveedorVO vo) throws Exception{
        if(vo == null) return;
        //RECUPERO EL USUARIO
        Usuario userModel = userService.findByKey(vo.getIdUsuario());
        //SI EL USUARIO ES NULO NO GUARDO EL PROVEEDOR
        if(userModel == null) return;
        
        Proveedor model = dao.findByKey(vo.getId());
        PropertyUtils.copyProperties(model, vo);
        model.setIdusuario(userModel);
        dao.update(model);
    }
    
    public void updateSolDEP(int idSol, int idProv) throws Exception{
        solDespService.updateEstado(idSol, idProv, "A");
        boolean flag = solDespService.existBySolWithStateN(idSol);
        if(!flag)
            solService.updateEstado(idSol, "DEP");        
    }
    
    public List<SolicitudVO> findGenOrdCompra(int idProv) throws Exception{
        return solService.findGenOrdCompra(idProv);
    }
    
    public SolicitudVO genOrdCompraByProv(int idSol, int codPrv) throws Exception{
        return solService.genOrdCompraByProv(idSol, codPrv);
    }
    
    public List<SolicitudVO> findCotAbierta(int codPrv) throws Exception{
        return solService.findCotAbierta(codPrv);
    }
    
    public List<SolicitudVO> findGenOrdCompraByProveedor(int codPrv) throws Exception{
        return solService.findGenOrdCompraByProveedor(codPrv);
    }
    
    public List<SolicitudVO> findDespProvByProveedorWinner(int codPrv) throws Exception{
        return solService.findDespProvByProveedorWinner(codPrv);
    }

}
