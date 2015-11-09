/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artemisa.dao;

import com.artemisa.domain.Parametro;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nicolasrubiano
 */
@org.springframework.transaction.annotation.Transactional(value="hibernateTransactionManager")
@Repository
public class ParametroDao extends BaseDao<Parametro> 
{    
    public static final String UPLOAD_PATH = "UPLOAD_PATH";
    
    private static final long serialVersionUID = -5044059926060067406L;
}
