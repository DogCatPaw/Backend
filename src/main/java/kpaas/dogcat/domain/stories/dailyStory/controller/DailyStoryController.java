package kpaas.dogcat.domain.stories.dailyStory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kpaas.dogcat.domain.stories.dailyStory.dto.DailyStoryReqDto;
import kpaas.dogcat.domain.stories.dailyStory.dto.DailyStoryResDto;
import kpaas.dogcat.domain.stories.dailyStory.service.DailyStoryCommandService;
import kpaas.dogcat.domain.stories.dailyStory.service.DailyStoryQueryService;
import kpaas.dogcat.global.apiPayload.CustomResponse;
import kpaas.dogcat.global.apiPayload.code.SuccessCode;
import kpaas.dogcat.global.auth.CurrentWalletAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "일상 일지 API")
@RestController
@RequestMapping("/api/story/daily")
@RequiredArgsConstructor
public class DailyStoryController {

    private final DailyStoryQueryService dailyStoryQueryService;
    private final DailyStoryCommandService dailyStoryCommandService;

    @Operation(summary = "일상 일지 작성", description = "일지 하나를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON201", description = "성공입니다"),
            @ApiResponse(responseCode = "MEMBER_404", description = "회원이 없습니다."),
            @ApiResponse(responseCode = "PET_404", description = "등록된 반려동물이 없습니다. 등록 먼저 해주세요!")
    })
    @PostMapping()
    public CustomResponse<DailyStoryResDto.WriteStoryResDto> create(
            @Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
            @RequestBody DailyStoryReqDto.WriteStoryReqDto dto) {
        DailyStoryResDto.WriteStoryResDto createdStory = dailyStoryCommandService.writeDailyStory(walletAddress, dto);
        return CustomResponse.onSuccess(SuccessCode.CREATED, createdStory);
    }

    @Operation(summary = "일상 일지 상세 조회", description = "일지 하나를 상세 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
            @ApiResponse(responseCode = "STORY_404", description = "등록된 일상 일지가 없습니다.")
    })
    @GetMapping("/{storyId}")
    public CustomResponse<DailyStoryResDto.StoryDetailDto> getStory(
            @Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
            @PathVariable Long storyId) {
        String memberId = (walletAddress != null) ? walletAddress : null;
        return CustomResponse.onSuccess(SuccessCode.OK, dailyStoryQueryService.getStoryDetail(storyId, memberId));
    }

    @Operation(summary = "메인화면 - 일상 일지 목록 조회", description = "일지 메인 화면의 일지 목록을 조회합니다.")
    @GetMapping("/stories")
    public CustomResponse<DailyStoryResDto.StoriesListDto> getStories(
            @Parameter(hidden = true) @CurrentWalletAddress String walletAddress,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "9") int size){
        String memberId = (walletAddress != null) ? walletAddress : null;
        DailyStoryResDto.StoriesListDto searchResult = dailyStoryQueryService.search(keyword, cursorId, size, memberId);
        return CustomResponse.onSuccess(SuccessCode.OK, searchResult);
    }

    @Operation(summary = "일지 삭제", description = "작성된 일지를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
            @ApiResponse(responseCode = "MEMBER_404", description = "회원이 없습니다."),
            @ApiResponse(responseCode = "STORY_404", description = "등록된 일상 일지가 없습니다."),
            @ApiResponse(responseCode = "COMMON401", description = "삭제할 권한이 없습니다.")

    })
    @DeleteMapping("/{storyId}")
    public CustomResponse<?> deleteStory(@PathVariable Long storyId,
                                         @Parameter(hidden = true) @CurrentWalletAddress String walletAddress){
        dailyStoryCommandService.delete(storyId, walletAddress);
        return CustomResponse.onSuccess(SuccessCode.OK);
    }
}
