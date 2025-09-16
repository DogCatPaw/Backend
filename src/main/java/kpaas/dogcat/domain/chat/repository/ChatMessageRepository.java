package kpaas.dogcat.domain.chat.repository;

import jakarta.transaction.Transactional;
import kpaas.dogcat.domain.chat.entity.ChatMessage;
import kpaas.dogcat.domain.chat.entity.ChatRoom;
import kpaas.dogcat.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomAndMemberNotAndIsReadFalse(ChatRoom chatRoom, Member reader);
    Optional<ChatMessage> findTop1ByChatRoomOrderByIdDesc(ChatRoom chatRoom);
    List<ChatMessage> findByChatRoomIdOrderByCreatedTimeAsc(Long roomId);
    Long countByChatRoomAndMemberNotAndIsReadFalse(ChatRoom room, Member member);
    @Modifying
    @Transactional
    @Query("UPDATE ChatMessage cm " +
            "SET cm.isRead = true " +
            "WHERE cm.chatRoom.id = :roomId " +
            "AND cm.member.id = :memberId " +
            "AND cm.isRead = false")
    int markMessagesAsRead(@Param("roomId") Long roomId, @Param("memberId") Long memberId);
}
