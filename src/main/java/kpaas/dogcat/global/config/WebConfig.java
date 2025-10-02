package kpaas.dogcat.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kpaas.dogcat.global.converter.OctetStreamReadMsgConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final ObjectMapper objectMapper;

    @Autowired
    public WebConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new OctetStreamReadMsgConverter(objectMapper));
    }
}
