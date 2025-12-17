package com.example.userservice.exception;

public class ResourceDuplicate extends RuntimeException {
    public ResourceDuplicate(String message){
        super(message);
    }
}
