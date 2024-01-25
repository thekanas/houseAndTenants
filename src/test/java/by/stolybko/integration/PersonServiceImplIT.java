package by.stolybko.integration;

import by.stolybko.database.dto.response.PersonResponseDTO;
import by.stolybko.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class PersonServiceImplIT extends IntegrationTestBase {

    @Autowired
    @SpyBean
    private PersonService personService;

    @Test
    void getByUuid() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(6);
        Page<PersonResponseDTO> personResponseDTOS = personService.getAll(Pageable.ofSize(20));
        PersonResponseDTO expected = personResponseDTOS.getContent().get(0);
        UUID uuid = expected.getUuid();

        for (int i = 0; i < 6; i++) {
            executor.submit(() -> {

                PersonResponseDTO actual = personService.getByUuid(uuid);

                assertThat(actual).isEqualTo(expected);
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        //verify(personService).getByUuid(uuid);
    }

    @Test
    void getAll() {

        var actualResult = personService.getAll(Pageable.ofSize(20));

        assertThat(actualResult.getTotalElements()).isEqualTo(10L);

    }

}