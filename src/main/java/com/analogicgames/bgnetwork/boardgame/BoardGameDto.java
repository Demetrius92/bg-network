package com.analogicgames.bgnetwork.boardgame;

import java.util.Set;

import jakarta.persistence.OneToMany;

public class BoardGameDto {
    
    private Long entityId;

    private String name;

    private String description;

    private int minPlayers;

    private int maxPlayers;    

    @OneToMany
    private Set<Mechanism> mechanisms;

    private Difficulty difficulty;

    private int duration;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Set<Mechanism> getMechanisms() {
        return mechanisms;
    }

    public void setMechanisms(Set<Mechanism> mechanisms) {
        this.mechanisms = mechanisms;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    
}
