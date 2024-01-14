package by.stolybko.util;

import by.stolybko.database.dto.HouseRequestDTO;
import by.stolybko.database.dto.HouseResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.PersonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with", toBuilder = true)
@Accessors(chain = true)
public class HouseTestData {

    @Builder.Default
    private UUID houseUuid = UUID.fromString("05486810-43dd-41e8-ab60-98aa2d200acb");

    @Builder.Default
    private String area = "55m2";

    @Builder.Default
    private String country = "TestCountry";

    @Builder.Default
    private String city = "TestCity";

    @Builder.Default
    private String street = "TestStreet";

    @Builder.Default
    private String number = "TestNumber";

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.of(2023, 1, 1, 1, 10, 30);

    @Builder.Default
    private List<PersonEntity> tenants = new ArrayList<>();

    @Builder.Default
    private List<PersonEntity> owners = new ArrayList<>();

    public HouseEntity buildHouseEntity() {
        return new HouseEntity(1L, houseUuid, area, country, city, street, number, createDate, tenants, owners);
    }

    public HouseRequestDTO buildHouseRequestDTO() {
        return new HouseRequestDTO(houseUuid, area, country, city, street, number, new ArrayList<>());
    }

    public HouseResponseDTO buildHouseResponseDTO() {
        return new HouseResponseDTO(houseUuid, area, country, city, street, number, createDate);
    }


}
