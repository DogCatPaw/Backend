package kpaas.dogcat.domain.story.comment.repository;

import kpaas.dogcat.domain.story.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Long countByStoryId(Long storyId);

    List<Comment> findByStoryIdOrderByCreatedAtAsc(Long storyId, Pageable pageable);
    List<Comment> findByStoryIdAndIdGreaterThanOrderByCreatedAtAsc(Long storyId, Long cursor, Pageable pageable);

    Object findByStoryId(Long storyId);
}
