package kpaas.dogcat.domain.auth;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public boolean register(String walletAddress){
        log.debug("[ 회원 등록 시도 ]");
        Optional<Auth> optAuth = authRepository.findByWalletAddress(walletAddress);
        if(optAuth.isEmpty()){
            Auth auth = Auth.builder()
                    .walletAddress(walletAddress)
                    .build();
            authRepository.save(auth);
            return true;
        } else {
            return false;
        }
    }

    public boolean login(String walletAddress){
        log.debug("[ 로그인 요청 ]: {}", walletAddress);
        Optional<Auth> byWalletAddress = authRepository.findByWalletAddress(walletAddress);
        if(!byWalletAddress.isPresent()){
            authRepository.findByWalletAddress(walletAddress)
                    .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));
            return false;
        }
        return true;
    }
}
