package kpaas.dogcat.domain.shelter.repository;

import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.shelter.entity.Shelter;

import java.util.List;

public interface ShelterQueryDsl {
    public List<Shelter> searchShelters(Long cursor,
                                        int size,
                                        Region region,
                                        String district,
                                        String keyword);
}
