package kpaas.dogcat.domain.shelter.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.shelter.entity.QShelter;
import kpaas.dogcat.domain.shelter.entity.Shelter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShelterQueryDslImpl implements ShelterQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Shelter> searchShelters(Long cursor,
                                        int size,
                                        Region region,
                                        String district,
                                        String keyword) {
        QShelter shelter = QShelter.shelter;
        BooleanBuilder builder = new BooleanBuilder();

        // 지역 필터
        if (region != null)
            builder.and(shelter.region.eq(region));

        // 세부 지역 필터
        if (district != null && !district.isEmpty())
            builder.and(shelter.district.eq(district));

        // 키워드 검색
        if (keyword != null && !keyword.isBlank()) {
            builder.and(shelter.shelterName.containsIgnoreCase(keyword));
        }

        // 커서 기반 페이징
        if (cursor != null)
            builder.and(shelter.id.lt(cursor));

        return jpaQueryFactory
                .selectFrom(shelter)
                .where(builder)
                .orderBy(shelter.id.desc())
                .limit(size)
                .fetch();
    }
}
