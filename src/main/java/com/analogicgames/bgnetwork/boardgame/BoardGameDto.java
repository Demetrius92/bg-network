package com.analogicgames.bgnetwork.boardgame;

import java.util.Set;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
