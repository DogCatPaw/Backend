package kpaas.dogcat.domain.adopt.repository;

import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdoptRepository extends JpaRepository<Adopt, Long> {

    boolean existsByPetId(Long petId);

    // status
    List<Adopt> findByStatusOrderByIdDesc(AdoptionStatus status, Pageable pageable);
    List<Adopt> findByStatusAndIdLessThanOrderByIdDesc(AdoptionStatus status, Long cursor, Pageable pageable);

    // region + status
    List<Adopt> findByRegionAndStatusOrderByIdDesc(Region region, AdoptionStatus status, Pageable pageable);
    List<Adopt> findByRegionAndStatusAndIdLessThanOrderByIdDesc(Region region, AdoptionStatus status, Long id, Pageable pageable);

    // region + district + status
    List<Adopt> findByRegionAndDistrictAndStatusOrderByIdDesc(Region region, String district, AdoptionStatus status, Pageable pageable);
    List<Adopt> findByRegionAndDistrictAndStatusAndIdLessThanOrderByIdDesc(Region region, String district, AdoptionStatus status, Long id, Pageable pageable);

    @Query("SELECT a FROM Adopt a JOIN FETCH a.pet WHERE a.id = :adoptId")
    Optional<Adopt> findWithPetById(@Param("adoptId") Long adoptId);
}
