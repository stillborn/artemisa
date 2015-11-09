/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artemisa.service.exceptions;

/**
 *
 * @author nicolasrubiano
 */
public class DecendantsObjectException extends Exception{
   
    private static final long serialVersionUID = 1L;

    public DecendantsObjectException() {
    }

    public DecendantsObjectException(String message) {
        super(message);
    }
}
