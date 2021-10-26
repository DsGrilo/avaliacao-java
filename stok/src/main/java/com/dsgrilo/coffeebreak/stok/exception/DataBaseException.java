package com.dsgrilo.coffeebreak.stok.exception;

import com.sun.jdi.connect.spi.ClosedConnectionException;

public class DataBaseException extends ClosedConnectionException {

    public DataBaseException(String msg){
        super(msg);
    }

}
