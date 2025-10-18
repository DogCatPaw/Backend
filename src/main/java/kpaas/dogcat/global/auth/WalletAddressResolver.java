package kpaas.dogcat.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
public class WalletAddressResolver implements HandlerMethodArgumentResolver {

    private static final String HEADER_NAME = "X-Wallet-Address";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentWalletAddress.class)
                && parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String walletAddress = request.getHeader(HEADER_NAME);

        if (walletAddress == null) {
            log.warn("[WalletAddressResolver] 🚫 No X-Wallet-Address header found.");
        } else {
            log.info("[WalletAddressResolver] ✅ Received X-Wallet-Address: {}", walletAddress);
        }

        return walletAddress;
    }
}
