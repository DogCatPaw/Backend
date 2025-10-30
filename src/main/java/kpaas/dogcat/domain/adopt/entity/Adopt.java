package kpaas.dogcat.domain.adopt.entity;

import jakarta.persistence.*;
import kpaas.dogcat.domain.adopt.dto.AdoptReqDto;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.chat.entity.ChatRoom;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.global.enums.PostType;
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

    @Column(nullable = false, length = 2000)
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

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private PostType postType = PostType.ADOPTION;

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

    public void update(AdoptReqDto.RegisterDto dto) {
        if (dto.getPetId() != null) this.pet = Pet.builder().id(dto.getPetId()).build();
        if (dto.getTitle() != null) this.title = dto.getTitle();
        if (dto.getContent() != null) this.content = dto.getContent();
        if (dto.getRegion() != null) this.region = dto.getRegion();
        if (dto.getDistrict() != null) this.district = dto.getDistrict();
        if (dto.getShelterName() != null) this.shelterName = dto.getShelterName();
        if (dto.getContact() != null) this.contact = dto.getContact();
        if (dto.getDeadline() != null) this.deadline = dto.getDeadline();
        if (dto.getStatus() != null) this.status = dto.getStatus();
        if (dto.getImages() != null) this.images = dto.getImages();
    }
}
