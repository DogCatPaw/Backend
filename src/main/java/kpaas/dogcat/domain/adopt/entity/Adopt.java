package kpaas.dogcat.domain.adopt.entity;

import jakarta.persistence.*;
import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.pet.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Adopt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private Region region;          // 광역시·도

    @Column(nullable = false)
    private String district;        // 군·구 (ex. "강남구")

    @Column(nullable = false)
    private String shelterName;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private LocalDate deadline;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "adopt", fetch = FetchType.LAZY)
    private Pet pet;
}
