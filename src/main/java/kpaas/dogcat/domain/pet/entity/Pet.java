package kpaas.dogcat.domain.pet.entity;

import jakarta.persistence.*;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.enums.Breed;
import kpaas.dogcat.domain.pet.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Pet {

    @Id
    private String did;

    @Column(nullable = false)
    private String petName;

    @Enumerated(EnumType.STRING)
    private Breed breed;

    @Column(nullable = false)
    private int old;

    private int weight;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String color;

    @Column(nullable = false)
    private String feature;

    private String health;      //중성화
    private String specifics;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
