package by.stolybko.controller;

import by.stolybko.database.dto.request.PersonRequestDTO;
import by.stolybko.database.dto.response.PersonResponseDTO;
import by.stolybko.service.PersonService;
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


@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;


    @Test
    void getAllPersons() throws Exception {
        // given
        Page<PersonResponseDTO> pagePersonResponseDTO = PersonTestData.builder()
                .build().buildPagePersonResponseDTO();

        Mockito.when(personService.getAll(any()))
                .thenReturn(pagePersonResponseDTO);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/persons"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void getByUuidPerson() throws Exception {
        // given
        PersonResponseDTO personResponseDTO = PersonTestData.builder()
                .build().buildPersonResponseDTO();
        Mockito.when(personService.getByUuid(personResponseDTO.getUuid()))
                .thenReturn(personResponseDTO);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/persons/" + personResponseDTO.getUuid()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid").value(personResponseDTO.getUuid().toString()));

    }

    @Test
    void getTenantsByHouseUuid() throws Exception {
        // given
        UUID houseUuid = HouseTestData.builder().build().getHouseUuid();
        PersonResponseDTO personResponseDTO = PersonTestData.builder()
                .build().buildPersonResponseDTO();

        Mockito.when(personService.getTenantsByHouseUuid(houseUuid))
                .thenReturn(List.of(personResponseDTO));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/persons/house/" + houseUuid))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..uuid").value(personResponseDTO.getUuid().toString()));
    }

    @Test
    void createPerson() throws Exception {
        // given
        PersonRequestDTO personRequestDTO = PersonTestData.builder()
                .build().buildPersonRequestDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(personRequestDTO);

        // when, then
        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());

    }

    @Test
    void updatePerson() throws Exception {
        // given
        UUID uuid = PersonTestData.builder().build().getPersonUuid();
        PersonRequestDTO personRequestDTO = PersonTestData.builder()
                .build().buildPersonRequestDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(personRequestDTO);

        // when, then
        mockMvc.perform(put("/persons/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());

    }

    @Test
    void deletePerson() throws Exception {
        // given
        UUID uuid = PersonTestData.builder().build().getPersonUuid();

        // when, then
        mockMvc.perform(delete("/persons/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());

    }
}