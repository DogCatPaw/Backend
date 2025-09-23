package kpaas.dogcat.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kpaas.dogcat.domain.member.dto.AuthRequestDTO;
import kpaas.dogcat.domain.member.dto.AuthResponseDTO;
import kpaas.dogcat.domain.member.entity.Member;

public interface AuthCommandService {
    AuthResponseDTO.SignupResponseDTO signUp(AuthRequestDTO.SignupRequestDTO dto);
    AuthResponseDTO.LoginResponseDTO login(AuthRequestDTO.LoginRequestDTO dto);
    void logout(HttpServletRequest request,  HttpServletResponse response);
    AuthResponseDTO.ReissueResponseDTO reissue(AuthRequestDTO.ReissueRequestDTO dto);
    Member findById(Long memberId);
    Member findByUsername(String username);
}
