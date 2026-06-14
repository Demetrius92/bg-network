package com.analogicgames.bgnetwork.boardgame;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataMechanismService implements MechanismService {
    @Autowired
    MechanismRepository mechanismRepository;

    @Override
    public List<Mechanism> getAllMechanims() {
        return mechanismRepository.findAll();
    }

    @Override
    public Mechanism getMechanism(Long id) throws MechanismNotFoundException {
        return mechanismRepository
                .findById(id)
                .orElseThrow(() -> new MechanismNotFoundException("Mechanism not found"));        
    }

    @Override
    public Mechanism registerMechanism(Mechanism mechanism) {
        return mechanismRepository.save(mechanism);   
    }

    @Override
    public void updateMechanims(Mechanism mechanism) throws MechanismNotFoundException {
        if (!mechanismRepository.existsById(mechanism.getEntityId()))
            throw new MechanismNotFoundException("Mechanism not found");
        mechanismRepository.save(mechanism);
    }

}
