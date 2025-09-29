package kpaas.dogcat.domain.auth;

import kpaas.dogcat.domain.member.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VcReqDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class registerVcDTO{
        public String walletAddress;
        public String vcJwt;
        public Type type;
    }
}
