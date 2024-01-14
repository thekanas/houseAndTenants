package by.stolybko.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "uuid")
@Entity
@Table(name = "house")
public class HouseEntity implements BaseEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private String area;
    private String country;
    private String city;
    private String street;
    private String number;

    @CreationTimestamp
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "house")
    private List<PersonEntity> tenants = new ArrayList<>();

    @ToString.Exclude
    @ManyToMany(mappedBy = "ownership")
    private List<PersonEntity> owners = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
    }

    public void addOwner(PersonEntity owner) {
        owners.add(owner);
        owner.getOwnership().add(this);
    }

}


