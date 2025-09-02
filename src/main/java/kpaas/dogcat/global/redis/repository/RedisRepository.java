package kpaas.dogcat.global.redis.repository;

import kpaas.dogcat.global.redis.entity.RedisRefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<RedisRefreshToken, Long> {
//    @NonNull
//    Optional<RedisRefreshToken> findById(Long id);
//    void deleteById(Long id);
    //기본 제공됨
}
