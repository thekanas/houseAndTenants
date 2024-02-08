package by.stolybko.database.dto.response;

import by.stolybko.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record HouseHistoryResponseDTO(
        UUID uuid,
        String area,
        String country,
        String city,
        String street,
        String number,

        @JsonFormat(pattern = Constants.TIME_FORMAT_RESPONSE)
        LocalDateTime createDate,

        @JsonFormat(pattern = Constants.TIME_FORMAT_RESPONSE)
        LocalDateTime exitDate) {

}
