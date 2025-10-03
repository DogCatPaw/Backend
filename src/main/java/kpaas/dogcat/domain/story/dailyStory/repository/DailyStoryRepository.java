package kpaas.dogcat.domain.story.dailyStory.repository;

import kpaas.dogcat.domain.story.dailyStory.entity.DailyStory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyStoryRepository extends JpaRepository<DailyStory, Long> {
    // 첫 페이지용 (cursor 없이 그냥 최신순)
    List<DailyStory> findAllByOrderByIdDesc(Pageable pageable);

    // cursorId보다 작은 스토리만 조회 (최신순)
    List<DailyStory> findByIdLessThanOrderByIdDesc(Long cursorId, Pageable pageable);
}
