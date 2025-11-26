package kpaas.dogcat.domain.shelter.entity;

import jakarta.persistence.*;
import kpaas.dogcat.domain.adopt.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shelter {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private String shelterName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Region region;

    private String district;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String address;
}
