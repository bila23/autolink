/*---------------------------------------------------------
* FILE: UserGroupsDao.java
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
import com.terzus.autolink.model.UserGroups;
import com.terzus.autolink.model.UserGroupsPK;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Terzus
 * <b>Created by: </b>will
 * <b>For: </b>autolink
 * <b>On: </b>Aug 4, 2020 8:32:33 PM
 * <b>Purpose</b> 
 * <p>
 *      
 * </p>
 */
@Stateless
public class UserGroupsDao extends Dao<UserGroups, UserGroupsPK>{

    public UserGroupsDao(){
        super(UserGroups.class);
    }
    
    @PersistenceContext(unitName = "PU")
    private EntityManager em;
    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }

}
