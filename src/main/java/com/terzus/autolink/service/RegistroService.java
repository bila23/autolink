/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.terzus.autolink.service;

import com.bila.framework.commons.Decode;
import com.bila.framework.dao.Dao;
import com.bila.framework.service.Service;
import com.terzus.autolink.dao.RegistroDao;
import com.terzus.autolink.dao.UsuarioDao;
import com.terzus.autolink.model.Registro;
import com.terzus.autolink.model.Usuario;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author josemauricioherrera
 */
@Stateless
public class RegistroService extends Service<Registro, Integer> {

    @Inject private RegistroDao dao;
    @Inject private UsuarioDao daoUsuario;
    
    @Override
    public Dao<Registro, Integer> getDao() {
        return dao;
    }

    public void save(Registro reg, String pass) throws Exception{
        Usuario user = new Usuario();
        user.setIdtipo(5);//Es tipo de ususario Cliente
        user.setUser(reg.getEmail());
        user.setPass(pass);
        //user.setPass(Decode.getInstance().encrypt(pass));
        user.setFechacreacion(new Date());
        user.setEstado("A");
        daoUsuario.save(user);
        reg.setIdusuario(user);
        reg.setFechacreacion(new Date());
        dao.save(reg);
    }
    
    public Registro getRegistroByUser(String user) throws Exception {
        return dao.getRegistroByUser(user);
    }

    public void updatePassword(Usuario usu) throws Exception{
        daoUsuario.update(usu);
    }
    
}
