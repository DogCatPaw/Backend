package kpaas.dogcat.domain.donate.donation.repository;

import kpaas.dogcat.domain.donate.donation.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
}
