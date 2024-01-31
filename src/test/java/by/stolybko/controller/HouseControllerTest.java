package by.stolybko.controller;

import by.stolybko.database.dto.request.HouseRequestDTO;
import by.stolybko.database.dto.response.HouseResponseDTO;
import by.stolybko.service.HouseService;
import by.stolybko.util.HouseTestData;
import by.stolybko.util.PersonTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(HouseController.class)
class HouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseService houseService;

    @Test
    void getAllHouses() throws Exception {
        // given
        Page<HouseResponseDTO> houseResponseDTOPage = HouseTestData.builder()
                .build().buildPageHouseResponseDTO();

        Mockito.when(houseService.getAll(any()))
                .thenReturn(houseResponseDTOPage);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/houses"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getByUuidHouse() throws Exception {
        // given
        HouseResponseDTO houseResponseDTO = HouseTestData.builder()
                .build().buildHouseResponseDTO();
        Mockito.when(houseService.getByUuid(houseResponseDTO.getUuid()))
                .thenReturn(houseResponseDTO);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/houses/" + houseResponseDTO.getUuid()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid").value(houseResponseDTO.getUuid().toString()));

    }

    @Test
    void getOwnershipByPersonUuid() throws Exception {
        // given
        UUID personUuid = PersonTestData.builder().build().getPersonUuid();
        HouseResponseDTO houseResponseDTO = HouseTestData.builder()
                .build().buildHouseResponseDTO();

        Mockito.when(houseService.getOwnershipByPersonUuid(personUuid))
                .thenReturn(List.of(houseResponseDTO));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/houses/owner/" + personUuid))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..uuid").value(houseResponseDTO.getUuid().toString()));

    }

    @Test
    void createHouse() throws Exception {
        // given
        HouseRequestDTO houseRequestDTO = HouseTestData.builder()
                .build().buildHouseRequestDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(houseRequestDTO);

        // when, then
        mockMvc.perform(post("/houses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateHouse() throws Exception {
        // given
        UUID uuid = HouseTestData.builder().build().getHouseUuid();
        HouseRequestDTO houseRequestDTO = HouseTestData.builder()
                .build().buildHouseRequestDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(houseRequestDTO);

        // when, then
        mockMvc.perform(put("/houses/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteHouse() throws Exception {
        // given
        UUID uuid = HouseTestData.builder().build().getHouseUuid();

        // when, then
        mockMvc.perform(delete("/houses/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }
}