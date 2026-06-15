package com.analogicgames.bgnetwork.boardgame;

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
        Mechanism newMechanism = new Mechanism("Dice Rolling", "Throw dice to get random results");
        testEntityManager.persist(newMechanism);

        Optional<Mechanism> retrievedMechanism = mechanismRepository.findById(newMechanism.getEntityId());

        assertThat(retrievedMechanism).contains(newMechanism);
    }

    @Test
    public void givenTwoMechanismsRegistered_whenFindAll_thenSuccess() {
        Mechanism newMechanism1 = new Mechanism("Dice Rolling", "Throw dice to get random results");
        Mechanism newMechanism2 = new Mechanism("Hand Management", "Players have a hand of cards they manage");
        testEntityManager.persist(newMechanism1);
        testEntityManager.persist(newMechanism2);

        List<Mechanism> mechanismsRetrieved = mechanismRepository.findAll();

        assertThat(mechanismsRetrieved.size()).isEqualTo(2);
        assertThat(mechanismsRetrieved).contains(newMechanism1, newMechanism2);
    }    

    @Test
    public void givenNewMechanism_whenSave_thenSuccess() {
        Mechanism newMechanism = new Mechanism("Dice Rolling", "Throw dice to get random results");
        
        mechanismRepository.save(newMechanism);

        Mechanism retrievedMechanism = testEntityManager.find(Mechanism.class, newMechanism.getEntityId());
        assertThat(retrievedMechanism).isEqualTo(newMechanism);
    }

    public void givenMechanismRegistered_whenUpdate_thenSuccess() {
        Mechanism newMechanism = new Mechanism("Dice Rolling", "Throw dice to get predeterminate results");
        testEntityManager.persist(newMechanism);

        newMechanism.setDescription("Throw dice to get random results");
        mechanismRepository.save(newMechanism); 

        Mechanism retrievedMechanism = testEntityManager.find(Mechanism.class, newMechanism.getEntityId());
        assertThat(newMechanism).isEqualTo(retrievedMechanism);
    }
}
