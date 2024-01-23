package by.stolybko.database.dto;

import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class HouseRequestDTO {

    String area;
    String country;
    String city;
    String street;
    String number;
    List<UUID> ownersUuid;

}
