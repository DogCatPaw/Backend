package kpaas.dogcat.domain.story.repository;

import kpaas.dogcat.domain.story.entity.Story;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    // 첫 페이지용 (cursor 없이 그냥 최신순)
    List<Story> findAllByOrderByIdDesc(Pageable pageable);

    // cursorId보다 작은 스토리만 조회 (최신순)
    List<Story> findByIdLessThanOrderByIdDesc(Long cursorId, Pageable pageable);
}
