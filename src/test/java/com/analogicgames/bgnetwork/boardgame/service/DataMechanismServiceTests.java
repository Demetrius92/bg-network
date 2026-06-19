package com.analogicgames.bgnetwork.boardgame.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.analogicgames.bgnetwork.boardgame.model.Mechanism;
import com.analogicgames.bgnetwork.boardgame.model.MechanismRepository;

@ExtendWith(MockitoExtension.class)
public class DataMechanismServiceTests {
    @Mock
    private MechanismRepository mechanismRepository;

    @InjectMocks
    private DataMechanismService dataMechanismService;

    @Test
    public void givenTwoMechanismsRegistered_whenGetAllMechanims_thenSuccess() {
        Mechanism mechanism1 = createMechanismDiceRolling();
        Mechanism mechanism2 = createMechanismHandManagement();
        when(mechanismRepository.findAll()).thenReturn(List.of(
            mechanism1,
            mechanism2
        ));

        List<Mechanism> retrievedMechanisms = dataMechanismService.getAllMechanims();

        assertThat(retrievedMechanisms.size()).isEqualTo(2);
        assertThat(retrievedMechanisms).contains(mechanism1);
        assertThat(retrievedMechanisms).contains(mechanism2);
        verify(mechanismRepository).findAll();
    }

    @Test
    public void givenMechanismRegistered_whenGetMechanism_thenSuccess() throws MechanismNotFoundException {
        Mechanism mechanismWithId = createMechanismDiceRolling();
        mechanismWithId.setEntityId(1L);
        when(mechanismRepository.findById(mechanismWithId.getEntityId()))
            .thenReturn(Optional.of(mechanismWithId));

        Mechanism retrievedMechanism = dataMechanismService.getMechanism(mechanismWithId.getEntityId());

        assertThat(retrievedMechanism).isEqualTo(mechanismWithId);
        verify(mechanismRepository).findById(mechanismWithId.getEntityId());
    }

    @Test
    public void givenNewMechanism_whenRegisterMechanism_thenSuccess() {
        Mechanism mechanism = createMechanismDiceRolling();
        Mechanism mechanismWithId = createMechanismDiceRolling();
        mechanismWithId.setEntityId(1L);
        when(mechanismRepository.save(mechanism)).thenReturn(mechanismWithId);

        Mechanism registeredMechanism = dataMechanismService.registerMechanism(mechanism);

        assertThat(registeredMechanism).isEqualTo(mechanismWithId);
        verify(mechanismRepository).save(mechanism);
    }

    @Test
    public void givenUpdatedMechanism_whenUpdateMechanism_thenSuccess() throws MechanismNotFoundException {
        Mechanism mechanismWithId = createMechanismDiceRolling();
        mechanismWithId.setEntityId(1L);
        when(mechanismRepository.existsById(mechanismWithId.getEntityId()))
            .thenReturn(true);

        dataMechanismService.updateMechanism(mechanismWithId);

        verify(mechanismRepository).save(mechanismWithId);
    }

    public Mechanism createMechanismDiceRolling() {
        Mechanism mechanismDiceRolling = new Mechanism("Dice Rolling", "Throw dice to get predeterminate results");
        
        return mechanismDiceRolling;
    }

    public Mechanism createMechanismHandManagement() {
        Mechanism mechanismHandManagement = new Mechanism("Hand Management", "Players have a hand of cards they manage");

        return mechanismHandManagement;
    }
}
