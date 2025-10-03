package kpaas.dogcat.domain.story.comment.service;

import kpaas.dogcat.domain.story.comment.converter.CommentConverter;
import kpaas.dogcat.domain.story.comment.dto.CommentResDTO;
import kpaas.dogcat.domain.story.comment.entity.Comment;
import kpaas.dogcat.domain.story.Story;
import kpaas.dogcat.domain.story.comment.repository.CommentRepository;
import kpaas.dogcat.domain.story.StoryRepository;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentQueryService {

    private final StoryRepository storyRepository;
    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;

    public Long getCommentCount(Long storyId){
        if (!storyRepository.existsById(storyId)) {
            throw new CustomException(ErrorCode.DAILYSTORY_NOTFOUND);
        }
        return commentRepository.countByStoryId(storyId);
    }

    public CommentResDTO.GetCommentListDTO getComments(Long storyId, Long cursor, int size) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new CustomException(ErrorCode.DAILYSTORY_NOTFOUND));

        Pageable pageable = PageRequest.of(0, size);
        List<Comment> comments;
        if (cursor == null) {
            comments = commentRepository.findByStoryIdOrderByCreatedAtAsc(storyId, pageable);
        } else {
            comments = commentRepository.findByStoryIdAndIdGreaterThanOrderByCreatedAtAsc(storyId, cursor, pageable);
        }

        List<CommentResDTO.GetCommentDTO> commentDTOList = comments.stream()
                .map(commentConverter::toGetCommentDTO)
                .toList();

        Long nextCursor = comments.isEmpty() ? null : comments.get(comments.size() - 1).getId();

        return CommentResDTO.GetCommentListDTO.builder()
                .commentList(commentDTOList)
                .nextCursor(nextCursor)
                .build();
    }
}
