package kpaas.dogcat.domain.member.repository;

import kpaas.dogcat.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByUsername(String username);
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
}
