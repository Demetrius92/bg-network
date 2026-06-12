package com.analogicgames.bgnetwork.boardgame;

public class MechanismNotFoundException extends Exception {

    public MechanismNotFoundException(String message) {
        super(message);
    }

    public MechanismNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
