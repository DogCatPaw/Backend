package kpaas.dogcat.domain.member.entity;

import jakarta.persistence.*;
import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donationList.entity.DonationList;
import kpaas.dogcat.domain.member.enums.Gender;
import kpaas.dogcat.global.payment.entity.Payment;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_address", nullable = false, unique = true)
    private String walletAddress;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String profileUrl;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private int old;

    @Column(nullable = false)
    private String phoneNumber;

    @Builder.Default
    private Integer boneBalance = 0;    // 보유한 뼈다귀 수량, 1뼈다귀 = 1000원

    @Builder.Default
    private Integer settledBalance = 0; // 정산된 후원금

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Pet> petList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Payment> payments;

    public void chargeBone(Integer amount) {
        if (!amount.equals(1000) && !amount.equals(5000)
                && !amount.equals(10000) && !amount.equals(20000)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_BALANCE);
        }
        this.boneBalance += amount;
    }

    public Integer decreaseBone(Integer amount) {
        if (this.boneBalance < amount) {
            throw new CustomException(ErrorCode.BONE_NOT_ENOUGH);
        }
        this.boneBalance -= amount;
        return boneBalance;
    }

    public void settleBone(Integer amount) {
        this.settledBalance += amount;
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Donation> donations;

    @OneToMany(mappedBy = "member")
    private List<DonationList> donationListList;
}
