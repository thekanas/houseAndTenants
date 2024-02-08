package by.stolybko.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CRUDService<RESPONSE, REQUEST> {

    RESPONSE getByUuid(UUID uuid);
    Page<RESPONSE> getAll(Pageable pageable);
    RESPONSE create(REQUEST dto);
    RESPONSE update(UUID uuid, REQUEST dto);
    void delete(UUID uuid);

}
