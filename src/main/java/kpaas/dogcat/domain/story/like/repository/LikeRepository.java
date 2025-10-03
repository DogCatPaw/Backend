package kpaas.dogcat.domain.story.like.repository;

import kpaas.dogcat.domain.story.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByStoryIdAndMemberId(Long storyId, Long memberId);
    void deleteByStoryIdAndMemberId(Long storyId, Long memberId);
    Long countByStoryId(Long storyId);
}
