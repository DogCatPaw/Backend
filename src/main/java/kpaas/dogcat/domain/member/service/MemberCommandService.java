package kpaas.dogcat.domain.member.service;

import kpaas.dogcat.domain.member.converter.MemberConverter;
import kpaas.dogcat.domain.member.dto.MemberReqDto;
import kpaas.dogcat.domain.member.dto.MemberResDto;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.repository.MemberRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    public MemberResDto.SignupResDto signUp(MemberReqDto.SignupReqDto dto) {

        // 1. 지갑과 닉네임은 고유해야함
        if (memberRepository.existsById(dto.getWalletAddress())) {
            throw new CustomException(ErrorCode.DUPLICATED_WALLET);
        }
        if (memberRepository.existsByNickname(dto.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }

        Member entity = memberConverter.toSignupEntity(dto);
        Member saved = memberRepository.save(entity);
        log.info("[ 회원가입 완료 - 지갑: {} ]", saved.getId());
        return memberConverter.toSignupResDto(saved);
    }
}
