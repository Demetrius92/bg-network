package com.analogicgames.bgnetwork.boardgame;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = RestMechanismController.class)
public class RestMechanismControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MechanismService mechanismService;

    @MockitoBean
    private MechanismMapper mechanismMapper;


    @Test
    void givenTwoMechanismsRegistered_whenListMechanisms_thenSuccess() throws Exception {
        List<Mechanism> mechanisms = List.of(
            createMechanismDiceRolling(),
            createMechanismHandManagement()
        );
        given(mechanismService.getAllMechanims()).willReturn(mechanisms);
        given(mechanismMapper.mechanismToDto(mechanisms.get(0)))
            .willReturn(createMechanismDiceRollingDto());
        given(mechanismMapper.mechanismToDto(mechanisms.get(1)))
            .willReturn(createMechanismHandManagementDto());

        mockMvc
            .perform(get("/mechanisms"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()", is(2)))
            .andExpect(
                jsonPath("$[0].entityId")
                    .value(mechanisms.get(0).getEntityId())
            )
            .andExpect(
                jsonPath("$[0].name")
                    .value(mechanisms.get(0).getName())
            )
            .andExpect(
                jsonPath("$[0].description")
                    .value(mechanisms.get(0).getDescription())
            )
            .andExpect(
                jsonPath("$[1].entityId")
                    .value(mechanisms.get(1).getEntityId())
            )            
            .andExpect(
                jsonPath("$[1].name")
                    .value(mechanisms.get(1).getName())
            )
            .andExpect(
                jsonPath("$[1].description")
                    .value(mechanisms.get(1).getDescription())
            );
            
            verify(mechanismMapper).mechanismToDto(mechanisms.get(0));
            verify(mechanismMapper).mechanismToDto(mechanisms.get(1));
            verify(mechanismService).getAllMechanims();
    }

    @Test
    public void givenMechanism_whenGetMechanism_thenSuccess() throws Exception {
        Mechanism mechanismWithId = createMechanismDiceRolling();
        mechanismWithId.setEntityId(1L);
        MechanismDto mechanismDtoWithId = createMechanismDiceRollingDto();
        mechanismDtoWithId.setEntityId(1L);
        given(mechanismService.getMechanism(mechanismWithId.getEntityId()))
            .willReturn(mechanismWithId);
        given(mechanismMapper.mechanismToDto(mechanismWithId))
            .willReturn(mechanismDtoWithId);

        mockMvc
            .perform(get("/mechanisms/{id}", mechanismWithId.getEntityId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.entityId")
                    .value(mechanismWithId.getEntityId())
            )
            .andExpect(
                jsonPath("$.name")
                    .value(mechanismWithId.getName())
            )
            .andExpect(
                jsonPath("$.description")
                    .value(mechanismWithId.getDescription())
            );

        verify(mechanismMapper).mechanismToDto(mechanismWithId);
        verify(mechanismService).getMechanism(mechanismWithId.getEntityId());
    }

    @Test
    public void givenMechanism_whenRegisterMechanism_thenSuccess() throws Exception {
        Mechanism mechanism = createMechanismDiceRolling();
        Mechanism mechanismWithId = createMechanismDiceRolling();
        mechanismWithId.setEntityId(1L);
        MechanismDto mechanismDto = createMechanismDiceRollingDto();
        MechanismDto mechanismDtoWithId = createMechanismDiceRollingDto();
        mechanismDtoWithId.setEntityId(1L);
        given(mechanismService.registerMechanism(mechanism)).willReturn(mechanismWithId);
        given(mechanismMapper.dtoToMechanism(mechanismDto)).willReturn(mechanism);
        given(mechanismMapper.mechanismToDto(mechanismWithId))
            .willReturn(mechanismDtoWithId);

        mockMvc.perform(
            post("/mechanisms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mechanismDto))
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.entityId")
                    .value(mechanismWithId.getEntityId())
            )
            .andExpect(
                jsonPath("$.name")
                    .value(mechanismWithId.getName())
            )
            .andExpect(
                jsonPath("$.description")
                    .value(mechanismWithId.getDescription())
            );

        verify(mechanismMapper).mechanismToDto(mechanismWithId);
        verify(mechanismMapper).dtoToMechanism(mechanismDto);
        verify(mechanismService).registerMechanism(mechanism);            
    }

    @Test
    public void givenMechanism_whenUpdateMechanism_thenSuccess() throws Exception {
        long entityToUpdateId = 1L;
        MechanismDto fieldsToUpdate = MechanismDto
            .builder()
            .description("Throw dice to get predeterminate results")
            .build();
        Mechanism mechanismToUpdateWithId = createMechanismDiceRolling();
        mechanismToUpdateWithId.setEntityId(1L);
        Mechanism updatedMechanismWithId = createMechanismDiceRolling();
        updatedMechanismWithId.setEntityId(1L);
        updatedMechanismWithId.setDescription(fieldsToUpdate.getDescription());
        given(mechanismService.getMechanism(entityToUpdateId)).willReturn(mechanismToUpdateWithId);        
        given(mechanismMapper.updateMechanismFromDto(fieldsToUpdate, mechanismToUpdateWithId))
            .willReturn(updatedMechanismWithId);

        mockMvc.perform(
            put("/mechanisms/{id}", entityToUpdateId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fieldsToUpdate))
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        verify(mechanismService).getMechanism(entityToUpdateId);
        verify(mechanismMapper).updateMechanismFromDto(fieldsToUpdate, mechanismToUpdateWithId);
        verify(mechanismService).updateMechanism(updatedMechanismWithId);
    }

    private Mechanism createMechanismDiceRolling() {
        Mechanism mechanismDiceRolling = new Mechanism("Dice Rolling", "Throw dice to get predeterminate results");
        
        return mechanismDiceRolling;
    }

    private Mechanism createMechanismHandManagement() {
        Mechanism mechanismHandManagement = new Mechanism("Hand Management", "Players have a hand of cards they manage");

        return mechanismHandManagement;
    }
    
    private MechanismDto createMechanismDiceRollingDto() {
        Mechanism mechanismDiceRolling = createMechanismDiceRolling();

        return MechanismDto
            .builder()
            .name(mechanismDiceRolling.getName())
            .description(mechanismDiceRolling.getDescription())
            .build();
    }

    private MechanismDto createMechanismHandManagementDto() {
        Mechanism mechanismHandManagement = createMechanismHandManagement();

        return MechanismDto
            .builder()
            .name(mechanismHandManagement.getName())
            .description(mechanismHandManagement.getDescription())
            .build();
    }
}
