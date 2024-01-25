package by.stolybko.database.dto.response;

import java.io.Serializable;

public interface BaseResponseDTO<T extends Serializable> {

   // void setUuid(T uuid);

    T getUuid();
}
