package kpaas.dogcat.domain.pet.entity;

import jakarta.persistence.*;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.enums.Breed;
import kpaas.dogcat.domain.pet.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    @Id
    private String did;

    @Column(nullable = false)
    private String petName;

    @Enumerated(EnumType.STRING)
    private Breed breed;
    private String customBreed;  // breed == OTHER 일 경우 사용자가 직접 입력한 품종

    @Column(nullable = false)
    private int old;

    private int weight;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String color;

    @Column(nullable = false)
    private String feature;

    @Column(nullable = false)
    private String health;

    private String specifics;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
