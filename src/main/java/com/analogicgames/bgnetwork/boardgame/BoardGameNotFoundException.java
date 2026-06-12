package com.analogicgames.bgnetwork.boardgame;

public class BoardGameNotFoundException extends Exception {

    public BoardGameNotFoundException(String message) {
        super(message);
    }

    public BoardGameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
