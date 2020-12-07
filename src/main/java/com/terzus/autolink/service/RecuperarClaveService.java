/*----------------------------------------------------------
* FILE: RecuperarClaveService.java
* PRODUCT: autolink
*----------------------------------------------------------
* CEL - UNIDAD DE INFORMÁTICA
* ÁREA DE DESARROLLO DE SISTEMAS
*----------------------------------------------------------
*/
package com.terzus.autolink.service;

import com.bila.framework.dao.Dao;
import com.bila.framework.service.Service;
import com.terzus.autolink.dao.RecuperarClaveDao;
import com.terzus.autolink.model.RecuperarClave;
import com.terzus.autolink.model.Usuario;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author CEL
 * <b>Created by </b>WJuarez
 * <b>for </b>autolink
 * <b>package </b>com.terzus.autolink.service
 * <b>on </b>12-04-2020 09:29:53 AM
 * <b>Purpose</b> 
 * <p>
 *  
 * </p>
 */
@Stateless
@LocalBean
public class RecuperarClaveService extends Service<RecuperarClave, Integer>{

    @Inject private RecuperarClaveDao dao;
    @Inject private UsuarioService usuarioService;

    @Override
    public Dao<RecuperarClave, Integer> getDao() {
        return dao;
    }
    
    public RecuperarClave findByClaveAndState(String clave, String state) throws Exception{
        if(clave == null || clave.equals("") || state == null || state.equals("")) return null;
        return dao.findByClaveAndState(clave, state);
    }
    
    public void changePassword(RecuperarClave model, String newPassword) throws Exception{
        if(model == null) return;
        Usuario user = usuarioService.findByUser(model.getUsuario());
        user.setPass(newPassword);
        usuarioService.update(user);
        model.setEstado("U");
        dao.update(model);
    }
}