package kpaas.dogcat.domain.donate.donation;

import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;

    public Donation findDonation(Long donationId) {
         return donationRepository.findById(donationId)
                .orElseThrow(() -> new CustomException(ErrorCode.DONATION_NOTFOUND));
    }
}
