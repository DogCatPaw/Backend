package kpaas.dogcat.domain.pet.service;

import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.pet.entity.Pet;
import kpaas.dogcat.domain.pet.repository.PetRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetQueryService {

    private final PetRepository petRepository;

    public Pet findById(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOTFOUND));
    }
}
