package kpaas.dogcat.domain.story.service;

import kpaas.dogcat.domain.story.repository.CommentRepository;
import kpaas.dogcat.domain.story.repository.StoryRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentQueryService {

    private final StoryRepository storyRepository;
    private final CommentRepository commentRepository;

    public Long getCommentCount(Long storyId){
        if (!storyRepository.existsById(storyId)) {
            throw new CustomException(ErrorCode.STORY_NOTFOUND);
        }
        return commentRepository.countByStoryId(storyId);
    }
}
