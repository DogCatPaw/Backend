package kpaas.dogcat.domain.stories.dailyStory.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kpaas.dogcat.domain.stories.story.entity.Story;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("DAILY")
public class DailyStory extends Story { }
