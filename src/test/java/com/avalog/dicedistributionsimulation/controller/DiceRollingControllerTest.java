package com.avalog.dicedistributionsimulation.controller;

import com.avalog.dicedistributionsimulation.dto.response.CombinationDto;
import com.avalog.dicedistributionsimulation.dto.response.DiceSimulationDto;
import com.avalog.dicedistributionsimulation.dto.response.ProbabilityDistributionDto;
import com.avalog.dicedistributionsimulation.services.DiceRollingService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import static com.avalog.dicedistributionsimulation.constants.RestApiUrlConstants.*;
import static com.avalog.dicedistributionsimulation.utility.ObjectFactory.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DiceRollingControllerTest {

  @Mock DiceRollingService diceRollingService;
  @InjectMocks DiceRollingController diceRollingController;
  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(diceRollingController).build();
  }

  @Test
  public void diceRolling_returnDiceSimulationDtoBodyWhenValidQueryParamsEntered()
      throws Exception {
    DiceSimulationDto diceRollerDtoList = getDiceRollerDtoList();
    when(diceRollingService.diceRolling(anyInt(), anyInt(), anyInt()))
        .thenReturn(diceRollerDtoList);

    mockMvc
        .perform(
            post(BASE + ROLLING_DICES)
                .param("numberOfDiceRolls", "100")
                .param("numOfDice", "3")
                .param("numOfSide", "6"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    verify(diceRollingService, times(1)).diceRolling(anyInt(), anyInt(), anyInt());
  }

  @Test
  public void getlistOfCombinations_returnCombinationDtoListWhenCall() throws Exception {
    List<CombinationDto> combinationDtoList = buildCombinationDtoList();
    when(diceRollingService.getSimulationCombinationList()).thenReturn(combinationDtoList);

    mockMvc
        .perform(get(BASE + LIST_OF_COMBINATION))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    verify(diceRollingService, times(1)).getSimulationCombinationList();
  }

  @Test
  public void getListOfProbability_returnProbabilityDistributionDtoBodyWhenCall() throws Exception {
    List<ProbabilityDistributionDto> probabilityDistributionDtoList =
        buildProbabilityDistributionDtoList();
    when(diceRollingService.getSimulationProbabilityList())
        .thenReturn(probabilityDistributionDtoList);

    mockMvc
        .perform(get(BASE + PROBABILITY))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    verify(diceRollingService, times(1)).getSimulationProbabilityList();
  }
}
