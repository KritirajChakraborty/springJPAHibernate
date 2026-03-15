package com.Kritiraj.SpringJPAHibernate.exception;

public class CabNotFoundException extends RuntimeException{
    public CabNotFoundException(String message) {
        super(message);
    }
}
