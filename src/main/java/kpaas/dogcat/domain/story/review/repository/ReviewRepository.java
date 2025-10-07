package kpaas.dogcat.domain.story.review.repository;

import kpaas.dogcat.domain.story.dailyStory.entity.DailyStory;
import kpaas.dogcat.domain.story.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByOrderByIdDesc(Pageable pageable);
    List<Review> findByIdLessThanOrderByIdDesc(Long cursorId, Pageable pageable);
    @Query("SELECT s FROM Review s " +
            "WHERE s.title LIKE %:keyword% " +
            "ORDER BY s.id DESC")
    List<Review> findByTitleContainingFirstPage(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT s FROM Review s " +
            "WHERE s.title LIKE %:keyword% AND s.id < :cursorId " +
            "ORDER BY s.id DESC")
    List<Review> findByTitleContainingAfterCursor(@Param("keyword") String keyword,
                                                  @Param("cursorId") Long cursorId,
                                                  Pageable pageable);
}
