package kpaas.dogcat.domain.donate.donation.repository;

import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donation.enums.DonationStatus;
import kpaas.dogcat.domain.pet.enums.Breed;

import java.util.List;

public interface DonationQueryDsl {
    List<Donation> searchDonations(Long cursor, int size,
                                   Breed breed,
                                   DonationStatus status,
                                   String keyword);

    List<Donation> findDonationsByWalletAddress(String walletAddress, Long cursor, int size);
}
