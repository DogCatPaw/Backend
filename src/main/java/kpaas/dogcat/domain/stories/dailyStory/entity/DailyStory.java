package kpaas.dogcat.domain.stories.dailyStory.entity;

import jakarta.persistence.*;
import kpaas.dogcat.domain.stories.story.entity.Story;
import kpaas.dogcat.global.enums.PostType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("DAILY")
public class DailyStory extends Story {

    @PrePersist
    public void prePersist() {
        setPostType(PostType.DAILY);
    }
}
