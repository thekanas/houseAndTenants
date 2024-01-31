package by.stolybko.database.dto.response;

import by.stolybko.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class HouseResponseDTO implements BaseResponseDTO<UUID> {

        UUID uuid;
        String area;
        String country;
        String city;
        String street;
        String number;

        @JsonFormat(pattern = Constants.TIME_FORMAT_RESPONSE)
        LocalDateTime createDate;

}
