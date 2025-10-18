package kpaas.dogcat.global.config;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swagger() {
        Info info = new Info().title("멍냥일지").description("Swagger API");

        String securityName = "X-Wallet-Address";

        Components components = new Components()
                .addSecuritySchemes(securityName,
                        new SecurityScheme()
                                .name(securityName)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .description("사용자 지갑주소 헤더"));

        // 문서 최상단에 이 보안 스키마를 적용하는 것 -> 기본적으로 jwt token을 요구하게 된다
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityName);

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/"))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
