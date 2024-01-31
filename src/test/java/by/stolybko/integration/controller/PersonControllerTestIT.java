package by.stolybko.integration.controller;

import by.stolybko.controller.PersonController;
import by.stolybko.database.repository.PersonRepository;
import by.stolybko.integration.IntegrationTestBase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
class PersonControllerTestIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;


    @Test
    void getAllPersons() throws Exception {
        // given
        String json = """
                {
                    "content": [
                        {
                            "uuid": "dc02ac84-3518-4839-aa4f-046ba89b9688",
                            "name": "TestName1",
                            "surname": "TestSurname1",
                            "sex": "MALE",
                            "passport": {
                                "passportSeries": "SS",
                                "passportNumber": "1234"
                            },
                            "createDate": "2024-01-24T18:00:00:000",
                            "updateDate": "2024-01-24T18:00:00:000"
                        },
                        {
                            "uuid": "7d2d01ac-c1f2-4505-bf0e-457d16c882d5",
                            "name": "TestName2",
                            "surname": "TestSurname2",
                            "sex": "FEMALE",
                            "passport": {
                                "passportSeries": "SS",
                                "passportNumber": "1235"
                            },
                            "createDate": "2024-01-24T18:00:00:000",
                            "updateDate": "2024-01-24T18:00:00:000"
                        },
                        {
                            "uuid": "36bd9573-2c3d-46f2-b6c8-323509a6520c",
                            "name": "TestName3",
                            "surname": "TestSurname3",
                            "sex": "MALE",
                            "passport": {
                                "passportSeries": "SS",
                                "passportNumber": "1236"
                            },
                            "createDate": "2024-01-24T18:00:00:000",
                            "updateDate": "2024-01-24T18:00:00:000"
                        }
                    ],
                    "metadata": {
                        "page": 0,
                        "size": 20,
                        "totalElements": 3
                    }
                }
                """;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/persons"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(jsonNode.toString()));

    }

    @Test
    void getByUuidPerson() throws Exception {
        // given
        String json = """
                      {
                            "uuid": "dc02ac84-3518-4839-aa4f-046ba89b9688",
                            "name": "TestName1",
                            "surname": "TestSurname1",
                            "sex": "MALE",
                            "passport": {
                                "passportSeries": "SS",
                                "passportNumber": "1234"
                            },
                            "createDate": "2024-01-24T18:00:00:000",
                            "updateDate": "2024-01-24T18:00:00:000"
                        }
                """;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/persons/dc02ac84-3518-4839-aa4f-046ba89b9688"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(jsonNode.toString()));

    }

    @Test
    void getByUuidPersonShouldGetCorrectMessage_WhenUuidNotFound() throws Exception {
        // given
        String json = """
                {
                    "fieldName": "uuid",
                    "message": "PersonResponseDTO with 946cfa18-a0d5-4e7d-b9fc-ea5477a5ca28 not found"
                }
                """;
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/persons/946cfa18-a0d5-4e7d-b9fc-ea5477a5ca28"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(jsonNode.toString()));

    }


    @Test
    void getTenantsByHouseUuid() throws Exception {
        // given
        String json = """
                [
                        {
                            "uuid": "dc02ac84-3518-4839-aa4f-046ba89b9688",
                            "name": "TestName1",
                            "surname": "TestSurname1",
                            "sex": "MALE",
                            "passport": {
                                "passportSeries": "SS",
                                "passportNumber": "1234"
                            },
                            "createDate": "2024-01-24T18:00:00:000",
                            "updateDate": "2024-01-24T18:00:00:000"
                        },
                        {
                            "uuid": "7d2d01ac-c1f2-4505-bf0e-457d16c882d5",
                            "name": "TestName2",
                            "surname": "TestSurname2",
                            "sex": "FEMALE",
                            "passport": {
                                "passportSeries": "SS",
                                "passportNumber": "1235"
                            },
                            "createDate": "2024-01-24T18:00:00:000",
                            "updateDate": "2024-01-24T18:00:00:000"
                        },
                        {
                            "uuid": "36bd9573-2c3d-46f2-b6c8-323509a6520c",
                            "name": "TestName3",
                            "surname": "TestSurname3",
                            "sex": "MALE",
                            "passport": {
                                "passportSeries": "SS",
                                "passportNumber": "1236"
                            },
                            "createDate": "2024-01-24T18:00:00:000",
                            "updateDate": "2024-01-24T18:00:00:000"
                        }
                ]
                """;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/persons/house/0b84f914-1308-4eb2-acc0-b8e362917f22"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(jsonNode.toString()));
    }

    @Test
    void createPersonShouldSaveRequestInBD_WhenInvoke() throws Exception {
        // given
        String jsonRequest = """
                {
                            "name": "TestName4",
                            "surname": "TestSurname4",
                            "sex": "MALE",
                            "passport": {
                                "passportSeries": "SS",
                                "passportNumber": "1238"
                            },
                            "houseUuid": "0b84f914-1308-4eb2-acc0-b8e362917f22"
                        }
                """;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeRequest = objectMapper.readTree(jsonRequest);

        // when, then
        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNodeRequest.toString())
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());

        assertThat(personRepository.findAll()).hasSize(4);

    }

    @Test
    void createPersonShouldReturnBadRequest_WhenRequestIsNotValid() throws Exception {
        // given
        String jsonRequest = """
                {
                     "name": "Y",
                     "surname": "T"
                }
                """;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeRequest = objectMapper.readTree(jsonRequest);

        // when, then
        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNodeRequest.toString())
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void deletePersonShouldDeleteEntityInBD_WhenInvoke() throws Exception {

        // when, then
        mockMvc.perform(delete("/persons/dc02ac84-3518-4839-aa4f-046ba89b9688")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());

        assertThat(personRepository.findAll()).hasSize(2);

    }
}
