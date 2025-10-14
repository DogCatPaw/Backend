package kpaas.dogcat.domain.donate.donation.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
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
                                          DonationStatus status) {
        QDonation donation = QDonation.donation;
        BooleanBuilder builder = new BooleanBuilder();

        // 기본 상태 필터
        if (status != null)
            builder.and(donation.status.eq(status));
        else
            builder.and(donation.status.eq(DonationStatus.ACTIVE));

        if (breed != null)
            builder.and(donation.pet.breed.eq(breed));

        if (cursor != null)
            builder.and(donation.id.lt(cursor));

        return jpaQueryFactory
                .selectFrom(donation)
                .where(builder)
                .orderBy(donation.id.desc())
                .limit(size)
                .fetch();
    }
}
