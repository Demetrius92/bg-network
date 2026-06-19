package com.analogicgames.bgnetwork.boardgame.service;

public class BoardGameNotFoundException extends Exception {

    public BoardGameNotFoundException(String message) {
        super(message);
    }

    public BoardGameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
