package kpaas.dogcat.domain.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Auth {

    @Id
    @Column(name = "wallet_address")
    private String walletAddress; // 기본 키

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}