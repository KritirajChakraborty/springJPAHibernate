package com.Kritiraj.SpringJPAHibernate.exception;

public class CabNotAssociatedWithAnyDriverException extends RuntimeException {
    public CabNotAssociatedWithAnyDriverException(String message) {
        super(message);
    }
}
