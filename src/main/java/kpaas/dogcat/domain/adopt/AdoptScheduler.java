package kpaas.dogcat.domain.adopt;

import jakarta.transaction.Transactional;
import kpaas.dogcat.domain.adopt.service.AdoptCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdoptScheduler {

    private final AdoptCommandService adoptCommandService;

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    @Transactional
    public void closeExpiredAdoption() {
        log.info("[ 입양 공고 마감 스케줄러 시작 ]");
        adoptCommandService.closeAdoption();
    }
}
