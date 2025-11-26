package kpaas.dogcat.domain.member.repository;

import kpaas.dogcat.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String>, MemberQueryDsl {

    Optional<Member> findByUsername(String username);
    boolean existsById(String memberId);
    Optional<Member> findById(String memberId);
    boolean existsByNickname(String nickname);
}
