package kpaas.dogcat.domain.shelter.repository;

import kpaas.dogcat.domain.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Long>, ShelterQueryDsl {
}
