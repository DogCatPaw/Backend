package kpaas.dogcat.domain.auth;

import kpaas.dogcat.domain.member.enums.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VcRepository extends CrudRepository<Vc, Long> {
    Optional<Vc> findByWalletAddressAndType(String walletAddress, Type type);
    @Query("SELECT v " +
            "FROM Vc v " +
            "JOIN v.auth a " +
            "WHERE a.walletAddress = :walletAddress " +
            "AND v.type = :type")
    Optional<Vc> findVcByWalletAddressAndType(@Param("walletAddress") String walletAddress,
                                              @Param("type") Type type);
}
