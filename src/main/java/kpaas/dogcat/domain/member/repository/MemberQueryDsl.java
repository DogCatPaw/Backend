package kpaas.dogcat.domain.member.repository;

import kpaas.dogcat.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberQueryDsl {
    List<Member> findMembersWithPetsByCursor(LocalDateTime cursor, int size);
}
