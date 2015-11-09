/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artemisa.service;

import com.artemisa.domain.BaseEntity;
import com.artemisa.service.exceptions.EntityExistsException;
import com.artemisa.service.exceptions.InvalidValueException;
import java.util.List;
import org.hibernate.HibernateException;

/**
 *
 * @author nicolasrubiano
 * @param <T>
 */
public interface IBaseService<T extends BaseEntity>
{
    T findOne(long id) throws HibernateException;
    
    T save(T entity) throws InvalidValueException, EntityExistsException, HibernateException;
    
    T update(T entity) throws EntityExistsException, HibernateException;
    
    void delete(T entity) throws HibernateException;
    
    List<T> list();
}
