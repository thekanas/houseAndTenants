package by.stolybko.database.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseRequestDTO {

    private UUID uuid;
    private String area;
    private String country;
    private String city;
    private String street;
    private String number;
    private List<UUID> ownersUuid;

}
