package by.stolybko.database.dto;

import by.stolybko.database.entity.Passport;
import by.stolybko.database.entity.enam.Sex;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDTO {

    private UUID uuid;
    private String name;
    private String surname;
    private Sex sex;
    private Passport passport;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss:SSS")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss:SSS")
    private LocalDateTime updateDate;

}
