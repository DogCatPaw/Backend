package kpaas.dogcat.domain.chat.stomp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompController {

    private final SimpMessageSendingOperations messageTemplate;
    private final ChatMessageService chatMessageService;


    // 방법2. MessageMapping만 활용 - 현재 이거 활용
    @MessageMapping("/room/{roomId}")
    public void sendMessage2(@DestinationVariable Long roomId, ChatReqDTO.ChatMessageReqDTO chatMessageReqDTO){
        log.info("[ sendMessage2 : {} ]", chatMessageReqDTO.getMessage());
        chatMessageService.saveMessage(chatMessageReqDTO);
        messageTemplate.convertAndSend("/topic/" + roomId, chatMessageReqDTO.getMessage());
    }

}
