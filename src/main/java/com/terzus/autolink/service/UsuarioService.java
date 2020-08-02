/*---------------------------------------------------------
* FILE: UsuarioService.java
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
import com.terzus.autolink.dao.UsuarioDao;
import com.terzus.autolink.model.Usuario;
import com.terzus.autolink.vo.UsuarioVO;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author CEL
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>May 24, 2020 3:06:59 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class UsuarioService extends Service<Usuario, Integer>{
    
    @Inject private UsuarioDao dao;

    @Override
    public Dao<Usuario, Integer> getDao() {
        return dao;
    }
    
    public String findNameUser(int user) throws Exception{
        if(user == 0) return null;
        Usuario usr = dao.findByKey(user);
        if(usr != null) return usr.getNombre();
        return null;
    }
    
    public Usuario findByUser(String user) throws Exception{
        if(user == null || user.equals("")) return null;
        return dao.findByUser(user);
    }
    
    public List<Usuario> findUserTaller() throws Exception{
        return dao.findByTipoAndActive(3);
    }
    
    public List<Usuario> findUserAseguradora() throws Exception{
        return dao.findByTipoAndActive(2);
    }
    
    public List<Usuario> findUserProveedor() throws Exception{
        return dao.findByTipoAndActive(4);
    }
    
    public void saveVO(UsuarioVO vo) throws Exception{
        if(vo == null) return;
        Usuario model = new Usuario();
        PropertyUtils.copyProperties(model, vo);
        model.setFechacreacion(new Date());
        model.setEstado("A");
        dao.save(model);
    }
    
    public boolean existUser(String user) throws Exception{
        if(user == null) return false;
        Usuario userExist = dao.findByUser(user);
        if(userExist == null) return false;
        return true;
    }

}
