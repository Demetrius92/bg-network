package com.analogicgames.bgnetwork.boardgame.service;

import java.util.List;

import com.analogicgames.bgnetwork.boardgame.model.Mechanism;

public interface MechanismService {

    List<Mechanism> getAllMechanims();

    Mechanism getMechanism(Long id) throws MechanismNotFoundException;

    Mechanism registerMechanism(Mechanism mechanism);

    void updateMechanism(Mechanism mechanism) throws MechanismNotFoundException;
}
