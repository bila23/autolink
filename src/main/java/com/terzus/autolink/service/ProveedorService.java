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
    
    @Inject ProveedorDao dao;
    @Inject UsuarioService userService;

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

}
