package kpaas.dogcat.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatReqDto {

    @Data
    @AllArgsConstructor
    public static class ChatMessageReqDto {
        private Long roomId;
        private String chatSenderId;
        private String message;
    }

    @Data
    @AllArgsConstructor
    public static class ChatRoomCreateDto {
        private Long adoptId;
        private String roomName;
    }

    @Data
    public static class ChatCardReqDto {
        private Long roomId;
    }
}
