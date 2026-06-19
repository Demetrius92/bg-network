package com.analogicgames.bgnetwork.boardgame.model;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MechanismRepositoryIntegrationTests {
    @Autowired
    private MechanismRepository mechanismRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void givenMechanismRegistered_whenFindById_thenSuccess() {
        Mechanism newMechanism = createMechanismDiceRolling();
        testEntityManager.persist(newMechanism);

        Optional<Mechanism> retrievedMechanism = mechanismRepository.findById(newMechanism.getEntityId());

        assertThat(retrievedMechanism).contains(newMechanism);
    }

    @Test
    public void givenTwoMechanismsRegistered_whenFindAll_thenSuccess() {
        Mechanism newMechanism1 = createMechanismDiceRolling();
        Mechanism newMechanism2 = createMechanismHandManagement();
        testEntityManager.persist(newMechanism1);
        testEntityManager.persist(newMechanism2);

        List<Mechanism> retrievedMechanisms = mechanismRepository.findAll();

        assertThat(retrievedMechanisms.size()).isEqualTo(2);
        assertThat(retrievedMechanisms).contains(newMechanism1, newMechanism2);
    }    

    @Test
    public void givenNewMechanism_whenSave_thenSuccess() {
        Mechanism newMechanism = createMechanismDiceRolling();
        
        mechanismRepository.save(newMechanism);

        Mechanism retrievedMechanism = testEntityManager.find(Mechanism.class, newMechanism.getEntityId());
        assertThat(retrievedMechanism).isEqualTo(newMechanism);
    }

    @Test
    public void givenMechanismRegistered_whenUpdate_thenSuccess() {
        Mechanism newMechanism = createMechanismDiceRolling();
        testEntityManager.persist(newMechanism);

        newMechanism.setDescription("Throw dice to get predeterminate results");
        mechanismRepository.save(newMechanism); 

        Mechanism retrievedMechanism = testEntityManager.find(Mechanism.class, newMechanism.getEntityId());
        assertThat(newMechanism).isEqualTo(retrievedMechanism);
    }

    private Mechanism createMechanismDiceRolling() {
        Mechanism mechanismDiceRolling = new Mechanism("Dice Rolling", "Throw dice to get predeterminate results");
        
        return mechanismDiceRolling;
    }

    private Mechanism createMechanismHandManagement() {
        Mechanism mechanismHandManagement = new Mechanism("Hand Management", "Players have a hand of cards they manage");

        return mechanismHandManagement;
    }
}
