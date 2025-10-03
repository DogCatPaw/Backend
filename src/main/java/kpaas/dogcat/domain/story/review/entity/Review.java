package kpaas.dogcat.domain.story.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import kpaas.dogcat.domain.story.Story;
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
@DiscriminatorValue("ADOPTION")
public class Review extends Story {

    @Column(nullable = false)
    private String adoptionAgency;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate adoptionDate;
}
