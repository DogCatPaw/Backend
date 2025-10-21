package kpaas.dogcat.domain.donate.donation.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kpaas.dogcat.domain.donate.donation.entity.Donation;
import kpaas.dogcat.domain.donate.donation.entity.QDonation;
import kpaas.dogcat.domain.donate.donation.enums.DonationStatus;
import kpaas.dogcat.domain.pet.enums.Breed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DonationQueryDslImpl implements DonationQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Donation> searchDonations(Long cursor, int size,
                                          Breed breed,
                                          DonationStatus status,
                                          String keyword) {
        QDonation donation = QDonation.donation;
        BooleanBuilder builder = new BooleanBuilder();

        // 기본 상태 필터
        if (status != null)
            builder.and(donation.status.eq(status));
        else
            builder.and(donation.status.eq(DonationStatus.ACTIVE));

        // 품종 필터
        if (breed != null)
            builder.and(donation.pet.breed.eq(breed));

        // 키워드 검색
        if (keyword != null && !keyword.isBlank()) {
            builder.and(donation.title.containsIgnoreCase(keyword));
        }

        // 커서 기반 페이징
        if (cursor != null)
            builder.and(donation.id.lt(cursor));

        return jpaQueryFactory
                .selectFrom(donation)
                .where(builder)
                .orderBy(donation.deadline.asc())
                .limit(size)
                .fetch();
    }
}
