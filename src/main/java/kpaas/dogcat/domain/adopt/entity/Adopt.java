package kpaas.dogcat.domain.adopt.entity;

import jakarta.persistence.*;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.chat.entity.ChatRoom;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(nullable = false)
    private String images;

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

    @Enumerated(EnumType.STRING)
    private AdoptionStatus status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime appliedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;   // 공고 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adopter_id")
    private Member adopter;  // 입양 신청자

    //펫이 먼저 존재하고 입양 공고가 붙기 때문에 펫을 주인으로 설정
    @OneToOne(mappedBy = "adopt", fetch = FetchType.LAZY)
    private Pet pet;

    @OneToMany(mappedBy = "adopt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoom> chatRooms;

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void apply(Member adopter) {
        this.adopter = adopter;
        this.appliedAt = LocalDateTime.now();
        this.status = AdoptionStatus.ADOPTING;
    }

    public void updateStatus(AdoptionStatus status) {
        this.status = status;
    }
}
