package kpaas.dogcat.domain.donate.donation.repository;

import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donation.enums.DonationStatus;
import kpaas.dogcat.domain.pet.enums.Breed;

import java.util.List;

public interface DonationQueryDsl {
    List<Donation> searchDonations(Long cursor, int size,
                                   Breed breed,
                                   DonationStatus status,
                                   String keyword);
}
