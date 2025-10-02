package kpaas.dogcat.domain.chat.service;

import kpaas.dogcat.domain.chat.dto.ChatReqDTO;
import kpaas.dogcat.domain.chat.dto.ChatResDTO;
import kpaas.dogcat.domain.chat.entity.ChatMessage;
import kpaas.dogcat.domain.chat.entity.ChatParticipant;
import kpaas.dogcat.domain.chat.entity.ChatRoom;
import kpaas.dogcat.domain.chat.repository.ChatMessageRepository;
import kpaas.dogcat.domain.chat.repository.ChatParticipantRepository;
import kpaas.dogcat.domain.chat.repository.ChatRoomRepository;
import kpaas.dogcat.domain.member.entity.Member;
import kpaas.dogcat.domain.member.service.AuthCommandService;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatParticipantService chatParticipantService;
    private final AuthCommandService authCommandService;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;

    public ChatMessage saveMessage(ChatReqDTO.ChatMessageReqDTO chatMessageReqDTO) {
        //채팅방 조회
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageReqDTO.getRoomId())
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOTFOUND));

        //채팅방과 멤버 조회
        ChatParticipant sender = chatParticipantService.findByMemberIdAndChatRoomId(
                chatMessageReqDTO.getMemberId(), chatRoom.getId());

        //메시지 저장
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .participant(sender)
                .member(sender.getMember())
                .chatMessage(chatMessageReqDTO.getMessage())
                .isRead(false)  // 추후 더 생각해볼것
                .build();

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        return savedMessage;
    }

    public ChatMessage getLatestMessage(ChatRoom room) {
        return chatMessageRepository.findTop1ByChatRoomOrderByIdDesc(room)
                .orElseThrow(() -> new CustomException(ErrorCode.MESSAGE_NOTFOUND));
    }

    public Long getUnreadCount(ChatRoom room, Member member) {
        return chatMessageRepository.countByChatRoomAndMemberNotAndIsReadFalse(room, member);
    }

    public List<ChatResDTO.ChatMessageResDTO> getChatMessages(Long roomId, Long memberId) {
        // 로그인된 사용자가 참여자가 맞는지 확인
        ChatParticipant participants = chatParticipantRepository.findByMemberIdAndChatRoomId(memberId, roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.PARTICIPANT_NO_AUTH));
        List<ChatMessage> chatMessages = chatMessageRepository.findByChatRoomIdOrderByCreatedTimeAsc(roomId);
        List<ChatResDTO.ChatMessageResDTO> Dtos = new ArrayList<>();
        for (ChatMessage c : chatMessages) {
            ChatResDTO.ChatMessageResDTO dto = ChatResDTO.ChatMessageResDTO.builder()
                    .messageId(c.getId())
                    .senderId(c.getMember().getId())      //메세지에서 id를 꺼내야 메세지 보낸 사람
                    .senderName(c.getMember().getUsername())
                    .message(c.getChatMessage())
                    .isRead(c.isRead())
                    .build();
            Dtos.add(dto);
        }
        return Dtos;
    }

    // 읽음 여부 바꿈
    public void messageRead(Long roomId, Long memberId){
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOTFOUND));

        Member member = authCommandService.findById(memberId);
        markAsRead(chatRoom, member);
    }

    // 채팅방에 속해있으면서 memberNot에 reader(나)을 넣으면 내가 아닌 사람이 읽지않은 경우를 가져옴
    public void markAsRead(ChatRoom chatRoom, Member member) {
        List<ChatMessage> unreadMessages =
                chatMessageRepository.findByChatRoomAndMemberNotAndIsReadFalse(chatRoom, member);
        for (ChatMessage chatMessage : unreadMessages) {
            chatMessage.markAsRead();
        }
    }
}