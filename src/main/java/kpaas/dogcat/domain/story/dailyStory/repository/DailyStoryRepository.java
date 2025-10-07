package kpaas.dogcat.domain.story.dailyStory.repository;

import kpaas.dogcat.domain.story.dailyStory.entity.DailyStory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DailyStoryRepository extends JpaRepository<DailyStory, Long> {
    // 첫 페이지용 (cursor 없이 그냥 최신순)
    List<DailyStory> findAllByOrderByIdDesc(Pageable pageable);

    // cursorId보다 작은 스토리만 조회 (최신순)
    List<DailyStory> findByIdLessThanOrderByIdDesc(Long cursorId, Pageable pageable);

    @Query("SELECT s FROM DailyStory s " +
            "WHERE s.title LIKE %:keyword% " +
            "ORDER BY s.id DESC")
    List<DailyStory> findByTitleContainingFirstPage(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT s FROM DailyStory s " +
            "WHERE s.title LIKE %:keyword% AND s.id < :cursorId " +
            "ORDER BY s.id DESC")
    List<DailyStory> findByTitleContainingAfterCursor(@Param("keyword") String keyword,
                                                      @Param("cursorId") Long cursorId,
                                                      Pageable pageable);

}
