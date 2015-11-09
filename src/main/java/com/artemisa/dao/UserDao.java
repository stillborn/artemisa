/*
 * Usero change this license header, choose License Headers in Project Properties.
 * Usero change this template file, choose Userools | Useremplates
 * and open the template in the editor.
 */
package com.artemisa.dao;

import com.artemisa.domain.User;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nicolasrubiano
 */
@org.springframework.transaction.annotation.Transactional(value="hibernateTransactionManager")
@Repository
public class UserDao
{
    private static final Log logger = LogFactory.getLog(UserDao.class);
    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("sessionFactory")
    protected SessionFactory sessionFactory;
    
   
    /**
     * Stores an instance of the entity class in the database
     * @param User Object
     * @return
     */
    public User save(User t) {
        try {
            this.sessionFactory.getCurrentSession().persist(t);
            this.sessionFactory.getCurrentSession().flush();
            this.sessionFactory.getCurrentSession().refresh(t);
        } catch (Exception e) {
            // UserODO handle and log exceptions properly
            logger.debug("Error: " + e, e);
        }
        return t;
    }
    
    
    public User findByField(String field, Object value)
    {
        if (field.isEmpty() || value == null) return null;
        
        String query = "from com.artemisa.domain.User where " + field + " = :value";
        
        List<User> list = this.sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("value", value)
                .list();
        
        if(list.isEmpty())
        {
            return null;
        }
        
        return (User)list.get(0);
    }
    
    public List<User> find(Map<String, String> conditions)
    {
        if (conditions.isEmpty()) return null;
        
        String whereClausule = "";
        
        for(Map.Entry<String, String> condition : conditions.entrySet())
        {
            if(whereClausule.isEmpty())
            {
                whereClausule = condition.getValue();
            }
            else
            {
                whereClausule = whereClausule + " " + condition.getKey() + " " + condition.getValue();
            }
        }
                
        String query = "from com.artemisa.domain.User where " + whereClausule;
        
        List<User> list = this.sessionFactory.getCurrentSession().createQuery(query)
                .list();
        
        if(list.isEmpty())
        {
            return null;
        }
        
        return list;
    }
    
    // UserODO: id Parameter should be Long
    public User find(Long id) {
        if (id == 0) return null;
        
        String query = "from com.artemisa.domain.User where id = :id";
        
        List<User> list = this.sessionFactory.getCurrentSession().createQuery(query)
                .setLong("id", id)
                .list();
        
        if(list.isEmpty())
        {
            return null;
        }
        
        return (User)list.get(0);
    }
    
    public User find(Class<User> type, Object id) {
        if (id == null) return null;
        
        String query = "from "+ type.getName() +" where id = :id";
        
        List<User> list = this.sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("id", id)
                .list();
        
        if(list.isEmpty())
        {
            return null;
        }
        
        return (User)list.get(0);
    }
    
    public void delete(User entity) {
        
        handleDependenciesBeforeDelete(entity);
        
        if (this.sessionFactory.getCurrentSession().contains(entity)) {
            this.sessionFactory.getCurrentSession().delete(entity);
        } else {
            User attached = find(entity.getId());
            if(attached != null)
            {
                this.sessionFactory.getCurrentSession().delete(attached);
            }
        }
        
        this.sessionFactory.getCurrentSession().flush();
        
    }
    
    /**
     * Userhis method is called to handle dependend entities before delete an entity
     * @param entity
     */
    protected void handleDependenciesBeforeDelete(User entity) {
        // overwrite this method in extending class, if required
        // to remove related entries or to cut dependencies from DB before delete
    }
    
    
    public User update(User item) {
        return (User) this.sessionFactory.getCurrentSession().merge(item);
    }
    
    public List<User> list() {
        
        String query = "from com.artemisa.domain.User";
        
        return this.sessionFactory.getCurrentSession().createQuery(query)
                .list();
    }    
}
