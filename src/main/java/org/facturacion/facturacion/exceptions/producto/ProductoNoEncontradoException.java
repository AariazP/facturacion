package org.facturacion.facturacion.exceptions.producto;

public class ProductoNoEncontradoException extends RuntimeException{

    public ProductoNoEncontradoException(String mensaje){
        super(mensaje);
    }
}
