package com.analogicgames.bgnetwork.boardgame;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class BoardGame {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;

    private String name;

    private String description;

    private int minPlayers;

    private int maxPlayers;    

    @OneToMany
    private Set<Mechanism> mechanisms;

    private Difficulty difficulty;

    private int duration;

    protected BoardGame() {}

    public BoardGame(String name, String description, int minPlayers, int maxPlayers, Set<Mechanism> mechanisms,
            Difficulty difficulty, int duration) {
        this.name = name;
        this.description = description;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.mechanisms = mechanisms;
        this.difficulty = difficulty;
        this.duration = duration;
    }

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

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
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

    @Override
    public String toString() {
        return "BoardGame [entityId=" + entityId + ", name=" + name + ", description=" + description + ", minPlayers="
                + minPlayers + ", maxPlayers=" + maxPlayers + ", mechanisms=" + mechanisms + ", difficulty=" + difficulty
                + ", duration=" + duration + "]";
    }
}
