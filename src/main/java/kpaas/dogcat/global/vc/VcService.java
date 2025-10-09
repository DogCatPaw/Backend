package kpaas.dogcat.global.vc;

import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VcService {

    private final VcJwtMapper vcJwtMapper;
    private final PetRepository petRepository;

    public List<VcReqDTO.PetVcDTO> parseMultipleVcs(VcReqDTO.PetVcSyncReqDTO req) {
        List<VcReqDTO.PetVcDTO> petDtos = new ArrayList<>();

        for (String vc : req.getVcJwt()) {
            VcReqDTO.PetVcDTO dto = VcJwtMapper.toPetVcDTO(vc);
            petDtos.add(dto);
            log.info("[ VC Parsed : issuer={}, DID={} ]", dto.getIssuer(), dto.getDID());

            // 🐶 DTO → 엔티티 변환
            Pet pet = Pet.builder()
                    .did(dto.getDID())
                    .petName(dto.getPetName())
                    .breed(dto.getBreed())
                    .old(dto.getOld())
                    .weight(dto.getWeight())
                    .gender(dto.getGender())
                    .color(dto.getColor())
                    .isNeutral(dto.isNeutral())
                    .specifics(dto.getSpecifics())
                    .issuer(dto.getIssuer())
//                    .ownerWallet(req.getMemberWallet()) // 요청 보낸 guardian wallet
                    .build();
        }


        return petDtos;
    }
}
