package kpaas.dogcat.domain.auth;

import jakarta.persistence.*;
import kpaas.dogcat.domain.member.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_address")
    private Auth auth;

    @Enumerated(EnumType.STRING)
    private Type type;
    private String vcJwt;
}
