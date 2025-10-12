package kpaas.dogcat.domain.pet.entity;

import jakarta.persistence.*;
import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.donate.donation.entity.Donation;
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
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Pet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String did;

    @Column(nullable = false)
    private String petProfile;

    private String petName;
    private int old;
    private int weight;
    private String color;
    private boolean isNeutral;      //중성화
    private String specifics;       //특이사항
//    private String issuer;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Breed breed;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donation> donations;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "adopt_id")
    private Adopt adopt;

    /** adopt 연결 메서드 */
    public void setAdopt(Adopt adopt) {
        this.adopt = adopt;
        if (adopt != null && adopt.getPet() != this) {
            adopt.setPet(this);
        }
    }
}
