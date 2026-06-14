package com.analogicgames.bgnetwork.boardgame;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MechanismDto {
    
    private Long entityId;

    private String name;
    
    private String description;
    
}
