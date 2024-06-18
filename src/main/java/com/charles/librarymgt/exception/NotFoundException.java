package com.charles.librarymgt.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(Class<?> clas, Long id) {
        super("Cannot find " + clas.getSimpleName() + " with id: " + id);
    }
}
