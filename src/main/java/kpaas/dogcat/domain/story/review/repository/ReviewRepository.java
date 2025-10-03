package kpaas.dogcat.domain.story.review.repository;

import kpaas.dogcat.domain.story.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByOrderByIdDesc(Pageable pageable);
    List<Review> findByIdLessThanOrderByIdDesc(Long cursorId, Pageable pageable);
}
