package com.analogicgames.bgnetwork.boardgame;

import java.util.List;

public interface MechanismService {

    List<Mechanism> getAllMechanims();

    Mechanism getMechanism(Long id) throws MechanismNotFoundException;

    Mechanism registerMechanism(Mechanism mechanism);

    void updateMechanims(Mechanism mechanism) throws MechanismNotFoundException;
}
