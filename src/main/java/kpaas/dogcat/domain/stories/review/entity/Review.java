package kpaas.dogcat.domain.stories.review.entity;

import jakarta.persistence.*;
import kpaas.dogcat.domain.stories.story.entity.Story;
import kpaas.dogcat.global.enums.PostType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("REVIEW")
public class Review extends Story {

    @PrePersist
    public void prePersist() { setPostType(PostType.REVIEW); }

    @Column(nullable = false)
    private String adoptionAgency;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate adoptionDate;
}
