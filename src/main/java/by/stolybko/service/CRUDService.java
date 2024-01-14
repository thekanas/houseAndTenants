package by.stolybko.service;

import java.util.List;
import java.util.UUID;

public interface CRUDService<Response, Request> {

    Response getByUuid(UUID uuid);
    List<Response> getAll();
    void create(Request dto);
    void update(UUID uuid, Request dto);
    void patch(UUID uuid, Request dto);
    void delete(UUID uuid);

}
