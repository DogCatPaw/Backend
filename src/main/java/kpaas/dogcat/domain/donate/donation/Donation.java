package kpaas.dogcat.domain.donate.donation;

import jakarta.persistence.*;
import kpaas.dogcat.domain.donate.donationList.DonationList;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
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

    @CreatedDate
    @Column(updatable = false)
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private Category category;      //후원목적: 수술비, 의료비

    @Column(nullable = false)
    private String content;

    private String images;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "donation", cascade = CascadeType.REMOVE)
    private List<DonationList> donationListList;
}
