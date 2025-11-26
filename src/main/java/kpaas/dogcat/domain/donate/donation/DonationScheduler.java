package kpaas.dogcat.domain.donate.donation;

import jakarta.transaction.Transactional;
import kpaas.dogcat.domain.donate.donation.service.DonationCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DonationScheduler {

    private final DonationCommandService donationCommandService;

    // 매일 자정에 마감된 후원글 상태 변경
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    public void closeExpiredDonations() {
        log.info("[ 후원 공고 마감 스케줄러 시작 ]");
        donationCommandService.closeDonation();
    }

    // 매월 20일 정각에 정산 금액 지급
    @Scheduled(cron = "0 0 0 20 * ?", zone = "Asia/Seoul")
    public void settleDonations() {
        log.info("[ 정산 스케줄러 시작 ]");
        donationCommandService.settleDonation();
    }
}
