package kpaas.dogcat.domain.member.service;

import kpaas.dogcat.domain.member.converter.AdminConverter;
import kpaas.dogcat.domain.member.dto.AdminResDto;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.enums.Role;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminQueryService {

    private final MemberRepository memberRepository;
    private final AdminConverter adminConverter;

    public void isAdmin(String walletAddress) {
        Member member = memberRepository.findById(walletAddress)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        log.info("[ 관리자 접속 ]");
        if (member.getRole() != Role.ADMIN) {
            throw new CustomException(ErrorCode.FORBIDDEN_403);
        }
    }

    @Transactional(readOnly = true)
    public AdminResDto.MemberListResponseDto memberList(LocalDateTime cursor, int size) {
        List<Member> members = memberRepository.findMembersWithPetsByCursor(cursor, size);

        LocalDateTime nextCursor = members.isEmpty() ? null
                : members.get(members.size() - 1).getCreatedAt();

        log.info("[ 가입된 회원 및 펫 조회 ]");
        AdminResDto.MemberListResponseDto responseDto = adminConverter.toMemberListResponseDto(members);
        responseDto.setNextCursor(nextCursor);
        return responseDto;
    }
}
