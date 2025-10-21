package kpaas.dogcat.global.redis.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kpaas.dogcat.domain.chat.dto.ChatReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPubSubService implements MessageListener {

    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public void publish(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(pattern);
        String payload = new String(message.getBody());
        log.info("Redis 메시지 수신 - 채널: {}, 메시지: {}", channel, payload);

        try {
            // 구독하고 있는 입장은 역직렬화해야하므로 readValue
            ChatReqDTO.ChatMessageReqDTO messageReqDTO = objectMapper.readValue(payload, ChatReqDTO.ChatMessageReqDTO.class);
            log.info("메시지 역직렬화 성공 - 방: {}, 발신자: {}, 내용: {}",
                    messageReqDTO.getRoomId(), messageReqDTO.getChatSenderId(), messageReqDTO.getMessage());

            // 직렬화하여 json으로 NestJS Gateway로 브로드캐스트
            String jsonMessage = objectMapper.writeValueAsString(messageReqDTO);
            redisTemplate.convertAndSend("nestjs:broadcast:" + messageReqDTO.getRoomId(), jsonMessage);
            log.info("메시지 브로드캐스트 완료 - 채널: nestjs:broadcast:{}", messageReqDTO.getRoomId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

