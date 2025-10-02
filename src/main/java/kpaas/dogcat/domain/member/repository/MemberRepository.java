package kpaas.dogcat.domain.member.repository;

import kpaas.dogcat.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
    boolean existsByWalletAddress(String walletAddress);
    Optional<Member> findByWalletAddress(String walletAddress);
    boolean existsByNickname(String nickname);
}
