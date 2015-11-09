package com.artemisa.dao;

import com.artemisa.domain.BaseEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.transaction.annotation.Transactional(value="hibernateTransactionManager")
public class BaseDao<T extends BaseEntity> implements Serializable{
    
    private static final Log logger = LogFactory.getLog(BaseDao.class);
    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("sessionFactory")
    protected SessionFactory sessionFactory;
    
    public BaseDao() {
    }
    
    private Class<T> type;
    /**
     * Default constructor
     *
     * @param type entity class
     */
    public BaseDao(Class<T> type) {
        this.type = type;
    }
    
    
    public Class<T> getType()
    {
        return this.type;
    };
    
    public void setType(Class<T> type) {
        this.type = type;
    }
    
    /**
     * Stores an instance of the entity class in the database
     * @param T Object
     * @return
     */
    public T save(T t) {
        try {
            this.sessionFactory.getCurrentSession().persist(t);
            this.sessionFactory.getCurrentSession().flush();
            this.sessionFactory.getCurrentSession().refresh(t);
        } catch (Exception e) {
            // TODO handle and log exceptions properly
            logger.debug("Error: " + e, e);
        }
        return t;
    }
    
    
    public T findByField(String field, Object value)
    {
        if (field.isEmpty() || value == null) return null;
        
        String query = "from "+ this.type.getName() +" where " + field + " = :value";
        
        List<T> list = this.sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("value", value)
                .list();
        
        if(list.isEmpty())
        {
            return null;
        }
        
        return (T)list.get(0);
    }
    
    public List<T> find(Map<String, String> conditions)
    {
        String query = "from "+ this.type.getName();
        
        if (!conditions.isEmpty()) 
        {
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
            
            query = "from "+ this.type.getName() +" where " + whereClausule;
        }
        
        List<T> list = this.sessionFactory.getCurrentSession().createQuery(query)
                .list();
        
        if(list.isEmpty())
        {
            return null;
        }
        
        return list;
    }
    
    public List<T> find(String order)
    {
        String query = "from "+ this.type.getName();
        
        if(!order.isEmpty())
        {
            query = query + " order by " + order; 
        }
        
        List<T> list = this.sessionFactory.getCurrentSession().createQuery(query)
                .list();
        
        if(list.isEmpty())
        {
            return null;
        }
        
        return list;
    }
    
    public List<T> find(Map<String, String> conditions, String order)
    {
        String query = "from "+ this.type.getName();
        
        if (!conditions.isEmpty()) 
        {
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
            
            query = "from "+ this.type.getName() +" where " + whereClausule;
        }
        
        if(!order.isEmpty())
        {
            query = query + " order by " + order; 
        }
        
        List<T> list = this.sessionFactory.getCurrentSession().createQuery(query)
                .list();
        
        if(list.isEmpty())
        {
            return null;
        }
        
        return list;
    }
    
     public List<T> find(Map<String, String> conditions, String order, int startIndex, int maxResult)
    {
        String query = "from "+ this.type.getName();
        
        if (!conditions.isEmpty()) 
        {
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
            
            query = "from "+ this.type.getName() +" where " + whereClausule;
        }
        
        if(!order.isEmpty())
        {
            query = query + " order by " + order; 
        }
        
        List<T> list = this.sessionFactory.getCurrentSession().createQuery(query)
                .setFirstResult(startIndex)
                .setMaxResults(maxResult)
                .list();
        
        if(list.isEmpty())
        {
            return null;
        }
        
        return list;
    }
    
    // TODO: id Parameter should be Long
    public T find(Long id) {
        if (id == 0) return null;
        
        String query = "from "+ this.type.getName() +" where id = :id";
        
        List<T> list = this.sessionFactory.getCurrentSession().createQuery(query)
                .setLong("id", id)
                .list();
        
        if(list.isEmpty())
        {
            return null;
        }
        
        return (T)list.get(0);
    }
    
    public T find(Class<T> type, Object id) {
        if (id == null) return null;
        
        String query = "from "+ type.getName() +" where id = :id";
        
        List<T> list = this.sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("id", id)
                .list();
        
        if(list.isEmpty())
        {
            return null;
        }
        
        return (T)list.get(0);
    }
    
    public Long count()
    {
         String query = "select count(e) from "+ type.getName() +" e ";
        
        List list = this.sessionFactory.getCurrentSession().createQuery(query)
                .list();
        
        if(list.isEmpty())
        {
            return 0L;
        }
        
        return (Long)list.get(0);
    }
    
    public Long count(Map<String, String> conditions)
    {
        String query = "select count(e) from "+ this.type.getName() + " e";
        
        if (!conditions.isEmpty()) 
        {
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
            
            query = "select count(e) from "+ this.type.getName() +" e where " + whereClausule;
        }
        
        List list = this.sessionFactory.getCurrentSession().createQuery(query)
                .list();
        
        if(list.isEmpty())
        {
            return 0L;
        }
        
        return (Long)list.get(0);
    }
    
    public void delete(T entity) {
        
        handleDependenciesBeforeDelete(entity);
        
        if (this.sessionFactory.getCurrentSession().contains(entity)) {
            this.sessionFactory.getCurrentSession().delete(entity);
        } else {
            T attached = find(entity.getId());
            if(attached != null)
            {
                this.sessionFactory.getCurrentSession().delete(attached);
            }
        }
        
        this.sessionFactory.getCurrentSession().flush();
        
    }
    
    /**
     * This method is called to handle dependend entities before delete an entity
     * @param entity
     */
    protected void handleDependenciesBeforeDelete(T entity) {
        // overwrite this method in extending class, if required
        // to remove related entries or to cut dependencies from DB before delete
    }
    
    
    public T update(T item) {
        return (T) this.sessionFactory.getCurrentSession().merge(item);
    }
    
    public List<T> list() {
        
        String query = "from " + this.type.getName();
        
        return this.sessionFactory.getCurrentSession().createQuery(query)
                .list();
    }
}
