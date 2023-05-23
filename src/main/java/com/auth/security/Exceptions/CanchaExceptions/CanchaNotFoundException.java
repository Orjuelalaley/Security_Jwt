package com.auth.security.Exceptions.CanchaExceptions;

public class CanchaNotFoundException extends  RuntimeException{
    public CanchaNotFoundException(long message) {
        super("No se encontro la cancha con el ID : " + message);
    }
}
