package kpaas.dogcat.domain.chat.repository;

import kpaas.dogcat.domain.chat.entity.ChatReadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatReadStatusRepository extends JpaRepository<ChatReadStatus, Long> {
}
