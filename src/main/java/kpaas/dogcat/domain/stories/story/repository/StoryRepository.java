package kpaas.dogcat.domain.stories.story.repository;

import kpaas.dogcat.domain.stories.story.entity.Story;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long>, StoryQueryDsl{
}
