package kpaas.dogcat.domain.stories.story.repository;

import kpaas.dogcat.domain.stories.story.entity.Story;
import kpaas.dogcat.global.enums.PostType;

import java.util.List;

public interface StoryQueryDsl {
    List<Story> findStoriesByWalletAddress(String walletAddress, Long cursor, int size, PostType type);
}