package kpaas.dogcat.domain.donate.donation.repository;

import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donation.enums.DonationStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByStatusIn(List<DonationStatus> active);
    List<Donation> findByStatus(DonationStatus donationStatus);
    boolean existsByPetIdAndMemberIdAndStatusIn(Long petId, Long memberId, List<DonationStatus> blockingStatuses);
    List<Donation> findTop3ByStatusOrderByDeadlineAsc(DonationStatus status, Pageable pageable);

    List<Donation> findByStatusOrderByIdDesc(DonationStatus status, Pageable pageable);
    List<Donation> findByStatusAndIdLessThanOrderByIdDesc(DonationStatus status, Long cursorId, Pageable pageable);
}
