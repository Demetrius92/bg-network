package com.analogicgames.bgnetwork.boardgame.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.analogicgames.bgnetwork.boardgame.model.BoardGame;
import com.analogicgames.bgnetwork.boardgame.model.BoardGameDto;
import com.analogicgames.bgnetwork.boardgame.model.BoardGameMapper;
import com.analogicgames.bgnetwork.boardgame.model.Difficulty;
import com.analogicgames.bgnetwork.boardgame.model.Mechanism;
import com.analogicgames.bgnetwork.boardgame.service.BoardGameService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = RestBoardGameController.class)
public class RestBoardGameControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BoardGameService boardGameService;

    @MockitoBean
    private BoardGameMapper boardGameMapper;

    @Test
    void givenTwoBoardGamesRegistered_whenListMechanisms_thenSuccess() throws Exception {
        List<BoardGame> boardGames = List.of(
            createBoardGameCatan(),
            createBoardGameCarcassonne()
        );
        given(boardGameService.getAllBoardGames()).willReturn(boardGames);
        given(boardGameMapper.boardGameToDto(boardGames.get(0)))
            .willReturn(createBoardGameCatanDto());
        given(boardGameMapper.boardGameToDto(boardGames.get(1)))
            .willReturn(createBoardGameCarcassonneDto());

        mockMvc
            .perform(get("/boardgames"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()", is(2)))
            .andExpectAll(createBoardGameJsonResultMatchers(boardGames.get(0), 0))
            .andExpectAll(createBoardGameJsonResultMatchers(boardGames.get(1), 1));

        verify(boardGameMapper).boardGameToDto(boardGames.get(0));
        verify(boardGameMapper).boardGameToDto(boardGames.get(1));
        verify(boardGameService).getAllBoardGames();
    }

    @Test
    public void givenBoardGame_whenGetBoardGame_thenSuccess() throws Exception {
        BoardGame boardGameWithId = createBoardGameCatan();
        boardGameWithId.setEntityId(1L);
        BoardGameDto boardGameDtoWithId = createBoardGameCatanDto();
        boardGameDtoWithId.setEntityId(1L);
        given(boardGameService.getBoardGame(boardGameWithId.getEntityId()))
            .willReturn(boardGameWithId);
        given(boardGameMapper.boardGameToDto(boardGameWithId))
            .willReturn(boardGameDtoWithId);

        mockMvc
            .perform(get("/boardgames/{id}", boardGameWithId.getEntityId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                createBoardGameJsonResultMatchers(boardGameWithId)
            );

        verify(boardGameMapper).boardGameToDto(boardGameWithId);
        verify(boardGameService).getBoardGame(boardGameWithId.getEntityId());
    }

    @Test
    public void givenBoardGame_whenRegisterBoardGame_thenSuccess() throws Exception {
        BoardGame boardGame = createBoardGameCatan();
        BoardGame boardGameWithId = createBoardGameCatan();
        boardGameWithId.setEntityId(1L);
        BoardGameDto boardGameDto = createBoardGameCatanDto();
        BoardGameDto boardGameDtoWithId = createBoardGameCatanDto();
        boardGameDtoWithId.setEntityId(1L);
        given(boardGameService.registerBoardGame(boardGame)).willReturn(boardGameWithId);
        given(boardGameMapper.dtoToBoardGame(boardGameDto)).willReturn(boardGame);
        given(boardGameMapper.boardGameToDto(boardGameWithId))
            .willReturn(boardGameDtoWithId);

        mockMvc.perform(
            post("/boardgames")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardGameDto))
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                createBoardGameJsonResultMatchers(boardGameWithId)
            );

        verify(boardGameMapper).boardGameToDto(boardGameWithId);
        verify(boardGameMapper).dtoToBoardGame(boardGameDto);
        verify(boardGameService).registerBoardGame(boardGame);
    }

    @Test
    public void givenBoardGame_whenUpdateBoardGame_thenSuccess() throws Exception {
        long entityToUpdateId = 1L;
        BoardGameDto fieldsToUpdate = BoardGameDto
            .builder()
            .description("A game in wich you have to fight very loud")
            .build();
        BoardGame boardGameToUpdateWithId = createBoardGameCatan();
        boardGameToUpdateWithId.setEntityId(1L);
        BoardGame updatedBoardGameWithId = createBoardGameCatan();
        updatedBoardGameWithId.setEntityId(entityToUpdateId);
        updatedBoardGameWithId.setDescription(fieldsToUpdate.getDescription());
        given(boardGameService.getBoardGame(entityToUpdateId)).willReturn(boardGameToUpdateWithId);
        given(boardGameMapper.updateBoardGameFromDto(fieldsToUpdate, boardGameToUpdateWithId))
            .willReturn(updatedBoardGameWithId);

        mockMvc.perform(
            put("/boardgames/{id}", entityToUpdateId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fieldsToUpdate))
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());
        
        verify(boardGameService).getBoardGame(entityToUpdateId);
        verify(boardGameMapper).updateBoardGameFromDto(fieldsToUpdate, boardGameToUpdateWithId);
        verify(boardGameService).updateBoardGame(updatedBoardGameWithId);
    }

    private BoardGame createBoardGameCatan() {
        Mechanism mechanismDiceRolling = new Mechanism("Dice Rolling", "Throw dice to get random results");
        Mechanism mechanismHandManagement = new Mechanism("Hand Management", "Players have a hand of cards they manage");
        List<Mechanism> mechanismsCatan = List.of(mechanismDiceRolling, mechanismHandManagement);
        BoardGame boardGameCatan = new BoardGame("Catan", "A game in wich you have to trade very loud", 3, 4, mechanismsCatan, Difficulty.BEGINNER, 60);
        
        return boardGameCatan;
    }

    private BoardGame createBoardGameCarcassonne() {
        Mechanism mechanismTilePlacement = new Mechanism("Tile Placement", "Placing tiles on the table");
        Mechanism mechanismAreaMajority = new Mechanism("Area Majority", "Have the majority of you game elements in a certain area");
        List<Mechanism> mechanismsCarcassonne = List.of(mechanismTilePlacement, mechanismAreaMajority);                
        BoardGame boardGameCarcassonne = new BoardGame("Carcassone", "A game where you build roads and castles", 2, 5, mechanismsCarcassonne, Difficulty.BEGINNER, 45);

        return boardGameCarcassonne;
    }
    
    private BoardGameDto createBoardGameCatanDto() {
        BoardGame boardGameCatan = createBoardGameCatan();

        return BoardGameDto
            .builder()
            .name(boardGameCatan.getName())
            .description(boardGameCatan.getDescription())
            .minPlayers(boardGameCatan.getMinPlayers())
            .maxPlayers(boardGameCatan.getMaxPlayers())
            .mechanisms(boardGameCatan.getMechanisms())
            .difficulty(boardGameCatan.getDifficulty())
            .duration(boardGameCatan.getDuration())
            .build();
    }
    
    private BoardGameDto createBoardGameCarcassonneDto() {
        BoardGame boardGameCarcassonne = createBoardGameCarcassonne();

        return BoardGameDto
            .builder()
            .name(boardGameCarcassonne.getName())
            .description(boardGameCarcassonne.getDescription())
            .minPlayers(boardGameCarcassonne.getMinPlayers())
            .maxPlayers(boardGameCarcassonne.getMaxPlayers())
            .mechanisms(boardGameCarcassonne.getMechanisms())
            .difficulty(boardGameCarcassonne.getDifficulty())
            .duration(boardGameCarcassonne.getDuration())
            .build();
    }

    private ResultMatcher[] createBoardGameJsonResultMatchers(BoardGame boardGame) {
        return createBoardGameJsonResultMatchers(boardGame, -1);
    }

    private ResultMatcher[] createBoardGameJsonResultMatchers(BoardGame boardGame, int index) {
        String pathPrefix = index == -1 ?  "$" : "$[" + String.valueOf(index) + "]";
        Field[] boardGameFields = BoardGame.class.getDeclaredFields(); 
        List<ResultMatcher> jsonResultMatchers = new ArrayList<>();
        for (Field field : boardGameFields) {
            String jsonFieldName = field.getName();
            if (jsonFieldName.equals("mechanisms")) {
                jsonResultMatchers.add(
                    jsonPath(pathPrefix + ".mechanisms[0].name")
                        .value(boardGame.getMechanisms().get(0).getName())
                );
                jsonResultMatchers.add(
                    jsonPath(pathPrefix + ".mechanisms[1].name")
                        .value(boardGame.getMechanisms().get(1).getName())                    
                );
            } else if (jsonFieldName.equals("difficulty")) {
                jsonResultMatchers.add(
                    jsonPath(pathPrefix + ".difficulty")
                        .value(is(boardGame.getDifficulty().toString()))
                );
                continue;                
            } else {
                String methodName = "get" + firsLetterUpperCase(field.getName());
                Method method;
                try {
                    method = BoardGame.class.getDeclaredMethod(methodName);
                    jsonResultMatchers.add(
                        jsonPath(pathPrefix + "." + jsonFieldName)
                            .value(is(method.invoke(boardGame)), method.getReturnType())                
                    );                
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonResultMatchers.stream().toArray(ResultMatcher[]::new);
    }

    private String firsLetterUpperCase(String string) {
        return 
            string.substring(0, 1).toUpperCase() +
            string.substring(1);
    }
}
