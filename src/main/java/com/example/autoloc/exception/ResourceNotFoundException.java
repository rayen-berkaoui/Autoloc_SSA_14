package com.example.autoloc.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String entite, Long id) {
        super(entite + " introuvable avec l'id " + id);
    }
}
