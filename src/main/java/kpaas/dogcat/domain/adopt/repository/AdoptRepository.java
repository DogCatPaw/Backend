package kpaas.dogcat.domain.adopt.repository;

import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptRepository extends JpaRepository<Adopt, Long> {
    // status
    List<Adopt> findByStatusOrderByIdDesc(AdoptionStatus status, Pageable pageable);
    List<Adopt> findByStatusAndIdLessThanOrderByIdDesc(AdoptionStatus status, Long cursor, Pageable pageable);

    // region + status
    List<Adopt> findByRegionAndStatusOrderByIdDesc(Region region, AdoptionStatus status, Pageable pageable);
    List<Adopt> findByRegionAndStatusAndIdLessThanOrderByIdDesc(Region region, AdoptionStatus status, Long id, Pageable pageable);

    // region + district + status
    List<Adopt> findByRegionAndDistrictAndStatusOrderByIdDesc(Region region, String district, AdoptionStatus status, Pageable pageable);
    List<Adopt> findByRegionAndDistrictAndStatusAndIdLessThanOrderByIdDesc(Region region, String district, AdoptionStatus status, Long id, Pageable pageable);
}
