package kpaas.dogcat.domain.story.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.story.dto.StoryReqDTO;
import kpaas.dogcat.domain.story.dto.StoryResDTO;
import kpaas.dogcat.domain.story.service.StoryCommandService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "일상 일지 API")
@RestController
@RequestMapping("/api/story")
@RequiredArgsConstructor
public class StoryController {

    private final StoryCommandService storyCommandService;

    @Operation(summary = "일상 일지 작성", description = "일지 하나를 작성합니다.")
    @PostMapping("/daily")
    public CustomResponse<StoryResDTO.writeStoryResDTO> create(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                               @RequestBody StoryReqDTO.writeStoryReqDTO dto) {
        StoryResDTO.writeStoryResDTO createdStory = storyCommandService.createStory(userDetails.getId(), dto);
        return CustomResponse.onSuccess(SuccessCode.CREATED, createdStory);
    }
}
