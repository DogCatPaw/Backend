package kpaas.dogcat.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.entity.QMember;
import kpaas.dogcat.domain.pet.entity.QPet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static kpaas.dogcat.domain.member.entity.QMember.member;
import static kpaas.dogcat.domain.pet.entity.QPet.pet;

@Repository
@RequiredArgsConstructor
public class MemberQueryDslImpl implements MemberQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Member> findMembersWithPetsByCursor(LocalDateTime cursor, int size) {
        QMember qMember = member;
        QPet qPet = pet;

        return jpaQueryFactory
                .selectFrom(member)
                .distinct()
                .leftJoin(member.pets, pet).fetchJoin()
                .where(cursor != null ? member.createdAt.lt(cursor) : null) // createdAt 기준
                .orderBy(member.createdAt.desc()) // 최신순
                .limit(size)
                .fetch();
    }
}
