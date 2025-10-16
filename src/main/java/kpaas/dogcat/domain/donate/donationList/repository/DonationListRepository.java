package kpaas.dogcat.domain.donate.donationList.repository;

import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donationList.entity.DonationList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationListRepository extends JpaRepository<DonationList, Long> {
    List<DonationList> findByDonationOrderByIdDesc(Donation donation, Pageable pageable);
    List<DonationList> findByDonationAndIdLessThanOrderByIdDesc(Donation donation, Long id, Pageable pageable);

    List<DonationList> findByMemberIdOrderByIdDesc(String memberId, Pageable pageable);
    List<DonationList> findByMemberIdAndIdLessThanOrderByIdDesc(String memberId, Long cursor, Pageable pageable);
    // 총 후원 금액
    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM DonationList d WHERE d.member.id = :memberId")
    Integer getTotalDonationAmount(@Param("memberId") String memberId);
}
