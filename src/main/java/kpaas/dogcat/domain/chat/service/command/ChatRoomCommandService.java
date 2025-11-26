package kpaas.dogcat.domain.chat.service.command;

import kpaas.dogcat.domain.chat.dto.ChatReqDto;
import kpaas.dogcat.domain.chat.service.query.ChatRoomQueryService;
import kpaas.dogcat.domain.member.service.MemberQueryService;
import kpaas.dogcat.global.apiPayload.code.CustomException;
import kpaas.dogcat.global.apiPayload.code.ErrorCode;
import org.springframework.transaction.annotation.Transactional;
import kpaas.dogcat.domain.adopt.entity.Adopt;
import kpaas.dogcat.domain.adopt.service.AdoptQueryService;
import kpaas.dogcat.domain.chat.dto.ChatResDTO;
import kpaas.dogcat.domain.chat.entity.ChatParticipant;
import kpaas.dogcat.domain.chat.entity.ChatRoom;
import kpaas.dogcat.domain.chat.entity.RoomStatus;
import kpaas.dogcat.domain.chat.repository.ChatRoomRepository;
import kpaas.dogcat.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomCommandService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomQueryService chatRoomQueryService;
    private final ChatParticipantCommandService chatParticipantCommandService;
    private final MemberQueryService memberQueryService;
    private final AdoptQueryService adoptQueryService;

    public ChatResDTO.ChatRoomCreatedDto createRoom(String initiatorId, ChatReqDto.ChatRoomCreateDto dto) {
        // 사용자 & 입양 공고 검증
        Member initiator = memberQueryService.findById(initiatorId);    // 입양원하는 사용자
        Adopt adopt = adoptQueryService.findById(dto.getAdoptId());
        Member target = memberQueryService.findById(adopt.getWriter().getId());          // 입양 공고 작성자
        log.info("[ 채팅방 생성 - 입양 채팅 신청자 = {}, 입양 공고 작성자 = {}, adoptId = {} ]",
                initiatorId, target.getId(), adopt.getId());

        // 중복방 체크하고 없으면 생성, 있으면 기존 채팅방 반환
        Optional<ChatRoom> existingRoom = chatRoomQueryService.findExistingRoom(initiatorId, target.getId(), adopt.getId());
        if(target.getId().equals(initiator.getId())) {
            throw new CustomException(ErrorCode.CHAT_CANNOT_WITH_SELF);
        }
        if(existingRoom.isPresent()) {
            ChatRoom chatRoom = existingRoom.get();
            return new ChatResDTO.ChatRoomCreatedDto(chatRoom.getId(), chatRoom.getRoomName());
        }
        ChatRoom chatRoom = ChatRoom.builder()
                .adopt(adopt)
                .initiatorId(initiatorId)
                .targetId(target.getId())
                .roomName(adopt.getTitle())
                .roomStatus(RoomStatus.OPEN)
                .build();
        chatRoomRepository.save(chatRoom);

        // 두 참여자 모두 추가
        List<ChatParticipant> participants = Arrays.asList(
                ChatParticipant.builder().chatRoom(chatRoom).member(initiator).build(),
                ChatParticipant.builder().chatRoom(chatRoom).member(target).build()
        );
        chatParticipantCommandService.saveAllParticipants(participants);

        return new ChatResDTO.ChatRoomCreatedDto(chatRoom.getId(), chatRoom.getRoomName());
    }
}
