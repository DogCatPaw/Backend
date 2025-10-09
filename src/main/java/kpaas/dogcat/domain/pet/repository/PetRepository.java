package kpaas.dogcat.domain.pet.repository;

import kpaas.dogcat.domain.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    boolean existsByDid(String did);
}
