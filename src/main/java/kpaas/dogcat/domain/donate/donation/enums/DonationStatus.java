package kpaas.dogcat.domain.donate.donation.enums;

public enum DonationStatus {
    ACTIVE,     // 후원 가능
    ACHIEVED,   // 목표 금액 달성
    CLOSED,     // 마감일 지남
    SETTLED     // 정산 완료
}
