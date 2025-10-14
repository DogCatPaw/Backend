package kpaas.dogcat.domain.adopt.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.adopt.entity.QAdopt;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.pet.enums.Breed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdoptQueryDslImpl implements AdoptQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Adopt> searchAdoptions(Long cursor,
                                       int size,
                                       AdoptionStatus status,
                                       Breed breed,
                                       Region region,
                                       String district) {
        QAdopt adopt = QAdopt.adopt;
        BooleanBuilder builder = new BooleanBuilder();

        // 기본 상태 필터
        if (status != null)
            builder.and(adopt.status.eq(status));
        else
            builder.and(adopt.status.eq(AdoptionStatus.ACTIVE));

        if (breed != null)
            builder.and(adopt.pet.breed.eq(breed));

        if (region != null)
            builder.and(adopt.region.eq(region));

        if (district != null && !district.isEmpty())
            builder.and(adopt.district.eq(district));

        if (cursor != null)
            builder.and(adopt.id.lt(cursor));

        return jpaQueryFactory
                .selectFrom(adopt)
                .where(builder)
                .orderBy(adopt.id.desc())
                .limit(size)
                .fetch();
    }
}
