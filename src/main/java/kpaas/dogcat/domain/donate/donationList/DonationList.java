package kpaas.dogcat.domain.donate.donationList;

import jakarta.persistence.*;
import kpaas.dogcat.domain.donate.donation.Donation;
import kpaas.dogcat.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "donation_list_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DonationList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer amount;     // 후원비

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_id")
    private Donation donation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
