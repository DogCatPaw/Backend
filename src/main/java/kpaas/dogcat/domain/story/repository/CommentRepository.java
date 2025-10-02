package kpaas.dogcat.domain.story.repository;

import kpaas.dogcat.domain.story.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    Long countByStoryId(Long storyId);
}
