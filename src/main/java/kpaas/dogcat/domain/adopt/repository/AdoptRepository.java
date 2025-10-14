package kpaas.dogcat.domain.adopt.repository;

import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.adopt.enums.AdoptionStatus;
import kpaas.dogcat.domain.adopt.enums.Region;
import kpaas.dogcat.domain.pet.enums.Breed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdoptRepository extends JpaRepository<Adopt, Long>, AdoptQueryDsl {

    boolean existsByPetId(Long petId);

    @Query("SELECT a FROM Adopt a JOIN FETCH a.pet WHERE a.id = :adoptId")
    Optional<Adopt> findWithPetById(@Param("adoptId") Long adoptId);

    List<Adopt> findTop3ByStatusOrderByDeadlineAsc(AdoptionStatus adoptionStatus, Pageable pageable);
}
