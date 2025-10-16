package kpaas.dogcat.domain.pet.repository;

import kpaas.dogcat.domain.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    boolean existsByDid(String did);
    List<Pet> findAllByMemberId(String memberId);
}
