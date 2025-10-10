package kpaas.dogcat.domain.donate.donationList.repository;

import kpaas.dogcat.domain.donate.donationList.entity.DonationList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationListRepository extends JpaRepository<DonationList, Long> {
}
