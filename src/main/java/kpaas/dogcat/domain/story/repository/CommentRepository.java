package kpaas.dogcat.domain.story.repository;

import kpaas.dogcat.domain.story.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    Long countByStoryId(Long storyId);

    List<Comment> findByStoryIdOrderByCreatedAtAsc(Long storyId, Pageable pageable);
    List<Comment> findByStoryIdAndIdGreaterThanOrderByCreatedAtAsc(Long storyId, Long cursor, Pageable pageable);
}
