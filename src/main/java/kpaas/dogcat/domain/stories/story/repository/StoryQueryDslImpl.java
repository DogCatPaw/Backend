package kpaas.dogcat.domain.stories.story.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kpaas.dogcat.domain.stories.story.entity.QStory;
import kpaas.dogcat.domain.stories.story.entity.Story;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoryQueryDslImpl implements StoryQueryDsl{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Story> findStoriesByWalletAddress(String walletAddress, Long cursor, int size) {
        QStory story = QStory.story;

        var query = queryFactory
                .selectFrom(story)
                .where(story.member.id.eq(walletAddress))
                .orderBy(story.id.desc())
                .limit(size);

        if (cursor != null) {
            query.where(story.id.lt(cursor));
        }

        return query.fetch();
    }
}
