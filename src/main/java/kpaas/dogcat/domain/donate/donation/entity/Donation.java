package kpaas.dogcat.domain.donate.donation.entity;

import jakarta.persistence.*;
import kpaas.dogcat.domain.donate.donation.dto.DonationReqDto;
import kpaas.dogcat.domain.donate.donation.enums.Category;
import kpaas.dogcat.domain.donate.donation.enums.DonationStatus;
import kpaas.dogcat.domain.donate.donationList.entity.DonationList;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Donation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer targetAmount;

    @Column(nullable = false)
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private Category category;              //후원목적: 수술비, 의료비

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DonationStatus status = DonationStatus.ACTIVE;       // 후원 상태

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, length = 2000)
    private String images;

    // 계좌 정보
    private String bankName;
    private String accountNumber;
    private String accountHolder;           // 예금주명

    // 정산 관련 필드
    @Builder.Default
    private Integer currentAmount = 0;      // 현재 누적 후원금

    @Builder.Default
    private Integer payoutAmount = 0;           // 실제 지급액

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "donation", cascade = CascadeType.REMOVE)
    private List<DonationList> donationListList;

    public void changeStatus(DonationStatus status) {
        this.status = status;
    }

    public void setPayoutAmount(Integer amount) {
        this.payoutAmount = amount;
    }

    public void updateCurrentAmount(Integer amount) {
        this.currentAmount = amount;
    }

    public void update(DonationReqDto.CreateDto dto) {
        if (dto.getTitle() != null) this.title = dto.getTitle();
        if (dto.getTargetAmount() != null) this.targetAmount = dto.getTargetAmount();
        if (dto.getDeadline() != null) this.deadline = dto.getDeadline();
        if (dto.getCategory() != null) this.category = dto.getCategory();
        if (dto.getContent() != null) this.content = dto.getContent();
        if (dto.getBankName() != null) this.bankName = dto.getBankName();
        if (dto.getAccountNumber() != null) this.accountNumber = dto.getAccountNumber();
        if (dto.getAccountHolder() != null) this.accountHolder = dto.getAccountHolder();
        if (dto.getImages() != null) this.images = dto.getImages();
        if (dto.getPetId() != null) this.pet = Pet.builder().id(dto.getPetId()).build();
    }
}
