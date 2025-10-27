package kpaas.dogcat.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode implements BaseCode {

    OK(HttpStatus.OK, "COMMON200", "성공적으로 처리했습니다."),
    UPDATED(HttpStatus.OK, "COMMON200", "성공적으로 수정했습니다."),
    CREATED(HttpStatus.CREATED, "COMMON201", "성공적으로 생성했습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "COMMON204", "성공적으로 삭제되었습니다.");

//    SignUp(, , );

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
