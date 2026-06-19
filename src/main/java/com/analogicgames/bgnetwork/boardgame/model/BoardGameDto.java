package com.analogicgames.bgnetwork.boardgame.model;

import java.util.List;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardGameDto {
    
    private Long entityId;

    private String name;

    private String description;

    private int minPlayers;

    private int maxPlayers;    

    @OneToMany
    private List<Mechanism> mechanisms;

    private Difficulty difficulty;

    private int duration;

}
