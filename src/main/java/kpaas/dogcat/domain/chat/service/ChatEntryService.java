package kpaas.dogcat.domain.chat.service;

import kpaas.dogcat.domain.chat.service.command.ChatMessageCommandService;
import kpaas.dogcat.domain.chat.service.query.ChatParticipantQueryService;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import kpaas.dogcat.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatEntryService {

    private final JwtUtil jwtUtil;
    private final AuthCommandService authCommandService;
    private final ChatParticipantQueryService chatParticipantQueryService;
    private final ChatMessageCommandService chatMessageCommandService;


    public void connectSocket(StompHeaderAccessor accessor) {
        // CONNECT 시점에는 사용자 인증만 확인
        String accessToken = extractAccessToken(accessor);
        String username = jwtUtil.getUsername(accessToken);
        log.info("[ STOMP CONNECT - 사용자: {} ]", username);

        // 사용자 정보를 세션에 저장 (웹소켓 세션 메모리에만 존재해서 서버 재시작하면 날아감)
        accessor.getSessionAttributes().put("username", username);
        Member member = authCommandService.findByUsername(username);
        accessor.getSessionAttributes().put("memberId", member.getId());
        accessor.setUser(() -> username);  // Principal 생성
    }

    public void subscribeSocket(StompHeaderAccessor accessor) {
        log.info("[ STOMP SUBSCRIBE - 방 참여 권한 검증 ]");
        String destination = accessor.getDestination();

        if (destination != null && destination.startsWith("/topic/")) {

            // destination에서 roomId 추출: /topic/{roomId}
            String[] parts = destination.split("/");
            Long roomId = Long.parseLong(parts[2]);
            String username = (String) accessor.getSessionAttributes().get("username");
            Long memberId = (Long) accessor.getSessionAttributes().get("memberId");

            if (!chatParticipantQueryService.isRoomParticipant(memberId, roomId)) {
                log.error("[ 방 {} 참여 권한 없음 - 사용자: {} ]", roomId, username);
                throw new CustomException(ErrorCode.ROOM_NO_AUTH);
            }
            log.info("[ 방 {} 구독 성공 - 사용자: {} ]", roomId, username);
            chatMessageCommandService.markAsReadCount(roomId, memberId);
        }
    }


    private String extractAccessToken(StompHeaderAccessor accessor) {
        String bearerToken = accessor.getFirstNativeHeader("Authorization");
        if (bearerToken == null || !jwtUtil.isValid(bearerToken.substring(7))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        return bearerToken.substring(7);
    }
}

