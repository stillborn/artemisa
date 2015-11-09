/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artemisa.dao;

import com.artemisa.domain.CompraRubro;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nicolasrubiano
 */
@org.springframework.transaction.annotation.Transactional(value="hibernateTransactionManager")
@Repository
public class CompraRubroDao extends BaseDao<CompraRubro> 
{
    private static final long serialVersionUID = 4752813814860322984L;
}
