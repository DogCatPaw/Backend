package kpaas.dogcat.domain.story.repository;

import kpaas.dogcat.domain.story.entity.Like;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends CrudRepository<Like, Long> {
    boolean existsByStoryIdAndMemberId(Long storyId, Long memberId);
    void deleteByStoryIdAndMemberId(Long storyId, Long memberId);
    Long countByStoryId(Long storyId);
}
