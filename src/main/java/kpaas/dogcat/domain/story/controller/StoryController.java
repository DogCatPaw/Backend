package kpaas.dogcat.domain.story.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.story.dto.StoryReqDTO;
import kpaas.dogcat.domain.story.dto.StoryResDTO;
import kpaas.dogcat.domain.story.service.StoryCommandService;
import kpaas.dogcat.domain.story.service.StoryQueryService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "일상 일지 API")
@RestController
@RequestMapping("/api/story")
@RequiredArgsConstructor
public class StoryController {

    private final StoryCommandService storyCommandService;
    private final StoryQueryService storyQueryService;

    @Operation(summary = "일상 일지 작성", description = "일지 하나를 작성합니다.")
    @PostMapping(value = "/daily", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CustomResponse<StoryResDTO.writeStoryResDTO> create(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                               @RequestPart("story") StoryReqDTO.writeStoryReqDTO dto,   // JSON DTO
                                                               @RequestPart(value = "image", required = false) MultipartFile image) {
        StoryResDTO.writeStoryResDTO createdStory = storyCommandService.createStory(userDetails.getId(), dto, image);
        return CustomResponse.onSuccess(SuccessCode.CREATED, createdStory);
    }

    @Operation(summary = "일상 일지 하나 조회", description = "일지 하나를 조회합니다.")
    @GetMapping("/daily/{stories}")
    public CustomResponse<StoryResDTO.StoryPreviewDTO> getStory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                @PathVariable Long stories) {
        return CustomResponse.onSuccess(SuccessCode.OK, storyQueryService.getStory(stories, userDetails.getId()));
    }

    @Operation(summary = "메인 일상 일지 목록 조회", description = "일지 메인 화면의 일지 목록을 조회합니다.")
    @GetMapping("/daily/stories")
    public CustomResponse<StoryResDTO.StoriesListDTO> getStories(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                 @RequestParam(required = false) Long cursorId,
                                                                 @RequestParam(defaultValue = "8") int size){
        return CustomResponse.onSuccess(SuccessCode.OK, storyQueryService.getStories(cursorId, size, userDetails.getId()));
    }
}
