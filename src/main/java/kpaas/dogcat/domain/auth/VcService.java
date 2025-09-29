package kpaas.dogcat.domain.auth;

import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VcService {

    private final AuthRepository authRepository;
    private final VcRepository vcRepository;

    //지갑이 있고, VC는 없는 상태에서 등록
    public VcResDTO.registerVcDTO registerVc(VcReqDTO.registerVcDTO dto){
        log.debug("[ VC 등록 시도 ]: {}", dto.getVcJwt());
        Optional<Auth> optAuth = authRepository.findByWalletAddress(dto.getWalletAddress());
        Optional<Vc> optVc = vcRepository.findVcByWalletAddressAndType(dto.getWalletAddress(), dto.getType());
        if(optAuth.isPresent()){
            if(optVc.isEmpty()){
                Vc vc = Vc.builder()
                        .auth(optAuth.get())
                        .vcJwt(dto.getVcJwt())
                        .type(dto.getType())
                        .build();
                vcRepository.save(vc);
                log.debug("[ VC 등록 성공 ]");
                return VcResDTO.registerVcDTO.builder()
                        .type(vc.getType())
                        .vcJwt(vc.getVcJwt())
                        .build();
            } else {
                log.debug("[ VC 등록 실패 ]");
                throw new CustomException(ErrorCode.CONFLICT_VC);
            }
        } else {
            log.debug("[ VC 등록 실패 ]");
            throw new CustomException(ErrorCode.WALLET_NOTFOUND);
        }
    }
}
