package kpaas.dogcat.domain.story.dailyStory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.story.dailyStory.dto.DailyStoryReqDTO;
import kpaas.dogcat.domain.story.dailyStory.dto.DailyStoryResDTO;
import kpaas.dogcat.domain.story.dailyStory.service.DailyStoryCommandService;
import kpaas.dogcat.domain.story.dailyStory.service.DailyStoryQueryService;
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
public class DailyStoryController {

    private final DailyStoryQueryService dailyStoryQueryService;
    private final DailyStoryCommandService dailyStoryCommandService;

    @Operation(summary = "일상 일지 작성", description = "일지 하나를 작성합니다.")
    @PostMapping(value = "/daily", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CustomResponse<DailyStoryResDTO.writeStoryResDTO> create(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                    @RequestPart("story") DailyStoryReqDTO.writeStoryReqDTO dto,
                                                                    @RequestPart(value = "image", required = false) MultipartFile image) {
        DailyStoryResDTO.writeStoryResDTO createdStory = dailyStoryCommandService.writeDailyStory(userDetails.getId(), dto, image);
        return CustomResponse.onSuccess(SuccessCode.CREATED, createdStory);
    }

    @Operation(summary = "일상 일지 하나 조회", description = "일지 하나를 조회합니다.")
    @GetMapping("/daily/{stories}")
    public CustomResponse<DailyStoryResDTO.StoryPreviewDTO> getStory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                     @PathVariable Long stories) {
        Long memberId = (userDetails != null) ? userDetails.getId() : null;
        return CustomResponse.onSuccess(SuccessCode.OK, dailyStoryQueryService.getStory(stories, memberId));
    }

    @Operation(summary = "메인 일상 일지 목록 조회", description = "일지 메인 화면의 일지 목록을 조회합니다.")
    @GetMapping("/daily/stories")
    public CustomResponse<DailyStoryResDTO.StoriesListDTO> getStories(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                      @RequestParam(required = false) Long cursorId,
                                                                      @RequestParam(defaultValue = "9") int size){
        Long memberId = (userDetails != null) ? userDetails.getId() : null;
        return CustomResponse.onSuccess(SuccessCode.OK, dailyStoryQueryService.getStories(cursorId, size, memberId));
    }

    @Operation(summary = "일상 일지 검색하기", description = "로그인 없이 일상 일지를 검색합니다.")
    @GetMapping("/daily/search")
    public CustomResponse<DailyStoryResDTO.StoriesListDTO> search(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                  @RequestParam(required = true) String keyword,
                                                                  @RequestParam(required = false) Long cursorId,
                                                                  @RequestParam(defaultValue = "9") int size){
        Long memberId = (userDetails != null) ? userDetails.getId() : null;
        DailyStoryResDTO.StoriesListDTO searchResult = dailyStoryQueryService.search(keyword, cursorId, size, memberId);
        return CustomResponse.onSuccess(SuccessCode.OK, searchResult);
    }
}
