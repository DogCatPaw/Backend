package kpaas.dogcat.domain.story.dailyStory.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kpaas.dogcat.domain.story.Story;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("DAILY")
public class DailyStory extends Story { }
