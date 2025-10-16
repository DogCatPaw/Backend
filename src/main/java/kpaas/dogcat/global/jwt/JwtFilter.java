//package kpaas.dogcat.global.jwt;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import kpaas.dogcat.global.apiPayload.code.CustomException;
//import kpaas.dogcat.global.apiPayload.code.ErrorCode;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//public class JwtFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//    private final CustomUserDetailsService customUserDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String uri = request.getRequestURI();
//
//        // ✅ 1. 인증이 필요 없는 경로는 바로 통과
//        if (isExcludedPath(uri)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        try {
//            // 1. 헤더에서 토큰 추출
//            String token = getToken(request);
//
//            // 2. 토큰 검증
//            if(token != null && jwtUtil.isValid(token)){
//
//                // 3. 토큰에서 사용자 불러오기
//                String username = jwtUtil.getUsername(token);
//
//                // 4. 시큐리티는 userDetails객체를 기반으로 인증과 권한 체크를 한다(그러기위해 db에서 갖고옴)
//                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//
//                // 5. Authentication객체: 현재 로그인한 사용자의 정보를 담음, password에 null
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                // 6. SecurityContext에 인증 정보 저
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//
//            filterChain.doFilter(request, response);
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.getWriter().write("Invalid or expired token");
//        }
//    }
//
//    private String getToken(HttpServletRequest request) {
//
//        String token = request.getHeader("Authorization");
//        if(token != null && token.startsWith("Bearer ")) {
//            return token.substring(7);
//        }
//        return null;
//    }
//
//    private boolean isExcludedPath(String uri) {
//        return uri.startsWith("/api/auth/")
//                || uri.startsWith("/swagger")
//                || uri.startsWith("/v3/api-docs");
//    }
//}
