package kpaas.dogcat.domain.adopt.repository;

import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.pet.enums.Breed;

import java.util.List;

public interface AdoptQueryDsl {
    List<Adopt> searchAdoptions(Long cursor, int size,
                                AdoptionStatus status,
                                Breed breed,
                                Region region,
                                String district);
}
