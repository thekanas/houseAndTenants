package by.stolybko.controller;

import by.stolybko.database.dto.response.HouseHistoryResponseDTO;
import by.stolybko.database.dto.response.PersonHistoryResponseDTO;
import by.stolybko.service.HouseHistoryService;
import by.stolybko.util.HouseHistoryTestData;
import by.stolybko.util.HouseTestData;
import by.stolybko.util.PersonTestData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(HouseHistoryController.class)
class HouseHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseHistoryService houseHistoryService;

    @Test
    void getPersonTenantHistoryByHouseUuid() throws Exception {
        // given
        UUID houseUuid = HouseTestData.builder().build().getHouseUuid();
        PersonHistoryResponseDTO personHistoryResponseDTO = HouseHistoryTestData.builder()
                .build().buildPersonHistoryResponseDTO();

        Mockito.when(houseHistoryService.getPersonTenantHistoryByHouseUuid(houseUuid))
                .thenReturn(List.of(personHistoryResponseDTO));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/history/tenants/house/" + houseUuid))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..uuid").value(personHistoryResponseDTO.uuid().toString()));

    }

    @Test
    void getPersonOwnerHistoryByHouseUuid() throws Exception {
        // given
        UUID houseUuid = HouseTestData.builder().build().getHouseUuid();
        PersonHistoryResponseDTO personHistoryResponseDTO = HouseHistoryTestData.builder()
                .build().buildPersonHistoryResponseDTO();

        Mockito.when(houseHistoryService.getPersonOwnerHistoryByHouseUuid(houseUuid))
                .thenReturn(List.of(personHistoryResponseDTO));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/history/owners/house/" + houseUuid))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..uuid").value(personHistoryResponseDTO.uuid().toString()));

    }

    @Test
    void getHousesHistoryByPersonTenantUuid() throws Exception {
        // given
        UUID personUuid = PersonTestData.builder().build().getPersonUuid();
        HouseHistoryResponseDTO houseHistoryResponseDTO = HouseHistoryTestData.builder()
                .build().buildHouseHistoryResponseDTO();

        Mockito.when(houseHistoryService.getHousesHistoryByPersonTenantUuid(personUuid))
                .thenReturn(List.of(houseHistoryResponseDTO));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/history/houses/tenant/" + personUuid))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..uuid").value(houseHistoryResponseDTO.uuid().toString()));

    }

    @Test
    void getHousesHistoryByPersonOwnerUuid() throws Exception {
        // given
        UUID personUuid = PersonTestData.builder().build().getPersonUuid();
        HouseHistoryResponseDTO houseHistoryResponseDTO = HouseHistoryTestData.builder()
                .build().buildHouseHistoryResponseDTO();

        Mockito.when(houseHistoryService.getHousesHistoryByPersonOwnerUuid(personUuid))
                .thenReturn(List.of(houseHistoryResponseDTO));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/history/houses/owner/" + personUuid))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..uuid").value(houseHistoryResponseDTO.uuid().toString()));

    }
}