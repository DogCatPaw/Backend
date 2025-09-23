package kpaas.dogcat.domain.chat.service;

import kpaas.dogcat.domain.chat.entity.ChatParticipant;
import kpaas.dogcat.domain.chat.entity.ChatRoom;
import kpaas.dogcat.domain.chat.repository.ChatParticipantRepository;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChatParticipantService {

    private final ChatParticipantRepository chatParticipantRepository;
    private final AuthCommandService authCommandService;

    public void saveAllParticipants(List<ChatParticipant> chatParticipants) {
        chatParticipantRepository.saveAll(chatParticipants);
    }

    // 참여자인지 검증
    public boolean isRoomParticipant(String username, Long roomId) {
        Member member = authCommandService.findByUsername(username);
        return chatParticipantRepository.existsByChatRoomIdAndMemberId(roomId, member.getId());
    }

    // 참여자 2명 중에서 나를 제외한 한명 (상대방)
    public Member getTargetMember(ChatRoom room, Member loginMember) {
        return room.getParticipants().stream()
                .map(ChatParticipant::getMember)
                .filter(member -> !member.getId().equals(loginMember.getId()))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.PARTICIPANT_NOT_FOUND));
    }

    public ChatParticipant findByMemberIdAndChatRoomId(Long memberId, Long roomId) {
        ChatParticipant participant = chatParticipantRepository.findByMemberIdAndChatRoomId(memberId, roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.PARTICIPANT_NOT_FOUND));
        return participant;
    }


}
