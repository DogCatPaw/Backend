package kpaas.dogcat.global.vc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class VcJwtMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static VcReqDTO.PetVcDTO toPetVcDTO(String vcJwt) {
        try {
            // JWT payload 부분만 추출 (header.payload.signature)
            String[] parts = vcJwt.split("\\.");
            if (parts.length < 2)
                throw new IllegalArgumentException("Invalid JWT format");

            // Base64URL decode → JSON 변환
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
            JsonNode payloadNode = objectMapper.readTree(payloadJson);

            // credentialSubject 노드 추출
            JsonNode csNode = payloadNode.path("vc").path("credentialSubject");

            //  credentialSubject → PetVcDTO로 변환
            VcReqDTO.PetVcDTO dto = objectMapper.treeToValue(csNode, VcReqDTO.PetVcDTO.class);

            // issuer(보호자 DID) 추가 매핑
            dto.setIssuer(payloadNode.path("iss").asText());
            dto.setDID(csNode.path("id").asText());

            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse VC JWT", e);
        }
    }

}
