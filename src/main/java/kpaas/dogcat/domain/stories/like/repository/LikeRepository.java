package kpaas.dogcat.domain.stories.like.repository;

import kpaas.dogcat.domain.stories.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByStoryIdAndMemberId(Long storyId, String memberId);
    void deleteByStoryIdAndMemberId(Long storyId, String memberId);
    Long countByStoryId(Long storyId);
}
