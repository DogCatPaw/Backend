package kpaas.dogcat.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements BaseCode {

    BAD_REQUEST_400(HttpStatus.BAD_REQUEST,
            "COMMON400",
            "잘못된 요청입니다"),
    UNAUTHORIZED_401(HttpStatus.UNAUTHORIZED,
            "COMMON401",
            "인증이 필요합니다"),
    FORBIDDEN_403(HttpStatus.FORBIDDEN,
            "COMMON403",
            "접근이 금지되었습니다"),
    NOT_FOUND_404(HttpStatus.NOT_FOUND,
            "COMMON404",
            "요청한 자원을 찾을 수 없습니다"),
    INTERNAL_SERVER_ERROR_500(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "COMMON500",
            "서버 내부 오류가 발생했습니다"),

    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "VALID_400", "잘못된 파라미터 입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_401" ,"유효한 토큰이 아닙니다." ),
    DUPLICATED_WALLET(HttpStatus.CONFLICT, "WALLET_409" , "이미 가입된 회원입니다."),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "AUTH_409", "중복된 닉네임입니다."),

    MEMBER_NOTFOUND(HttpStatus.NOT_FOUND, "MEMBER_404", "회원이 없습니다."),
    WALLET_NOTFOUND(HttpStatus.NOT_FOUND, "WALLET_404", "회원(지갑)이 없습니다."),
    MISSING_WALLET_HEADER(HttpStatus.BAD_REQUEST, "AUTH400", "요청 헤더에 X-Wallet-Address가 없습니다."),

    ROOM_NOTFOUND(HttpStatus.NOT_FOUND, "ROOM_404", "채팅방이 없습니다."),
    PARTICIPANT_NOTFOUND(HttpStatus.NOT_FOUND, "PARTICIPANT_404", "채팅 참여자가 없습니다."),
    PARTICIPANT_NO_AUTH(HttpStatus.UNAUTHORIZED, "PARTICIPANT_403", "해당 채팅방의 참여자 권한이 없습니다."),
    ROOM_NO_AUTH(HttpStatus.UNAUTHORIZED, "ROOM_403" ,"해당 채팅방에 권한이 없습니다."),
    MESSAGE_NOTFOUND(HttpStatus.NOT_FOUND, "MESSAGE_404", "채팅 메시지가 없습니다."),
    CHAT_CANNOT_WITH_SELF(HttpStatus.CONFLICT, "ROOM_409" , "자신을 단독 참여자로 채팅방 생성이 불가합니다."),

    PET_NOTFOUND(HttpStatus.NOT_FOUND, "PET_404", "등록된 반려동물이 없습니다. 등록 먼저 해주세요!"),

    DAILYSTORY_NOTFOUND(HttpStatus.NOT_FOUND, "STORY_404", "등록된 일상 일지가 없습니다."),
    REVIEW_NOTFOUND(HttpStatus.NOT_FOUND, "STORY_404", "등록된 입양 후기가 없습니다."),
    IMAGE_REQUIRED(HttpStatus.NOT_FOUND, "STORY_404", "이미지 파일을 업로드해주세요."),
    COMMENT_NOTFOUND(HttpStatus.NOT_FOUND, "COMMENT_404", "해당되는 댓글이 없습니다."),

    // 후원 결제 관련 에러 추가
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "PAYMENT400", "결제 금액은 1000원 이상만 가능합니다."),
    ITEM_NOTFOUND(HttpStatus.NOT_FOUND, "ITEM404", "구매할 물품이 없습니다."),
    PAYMENT_NOTFOUND(HttpStatus.NOT_FOUND, "PAYMENT404", "결제가 없습니다."),
    PAYMENT_AMOUNT_MISMATCH(HttpStatus.BAD_REQUEST, "PAYMENT400", "결제 금액이 일치하지 않습니다."),
    PAYMENT_WAITING_FOR_DEPOSIT(HttpStatus.BAD_REQUEST, "PAYMENT400", "가상계좌 입금이 아직 완료되지 않았습니다."),
    PAYMENT_IN_PROGRESS(HttpStatus.BAD_REQUEST, "PAYMENT400", "결제 인증은 완료되었으나, 아직 최종 승인되지 않았습니다."),
    PAYMENT_CANCELED(HttpStatus.BAD_REQUEST, "PAYMENT400", "결제가 취소되었습니다."),
    PAYMENT_ABORTED(HttpStatus.BAD_REQUEST, "PAYMENT400", "결제 승인이 실패했습니다."),
    PAYMENT_EXPIRED(HttpStatus.BAD_REQUEST, "PAYMENT400", "결제 유효 시간이 만료되어 결제가 취소되었습니다."),
    PAYMENT_PARTIAL_CANCELED(HttpStatus.BAD_REQUEST, "PAYMENT400", "결제가 부분 취소되었습니다."),
    PAYMENT_UNSPECIFIED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "PAYMENT500", "결제/환불 상태가 불분명하여 처리에 실패했습니다."),
    PAYMENT_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "PAYMENT500", "토스 결제 응답이 없습니다."),

    // 후원 뼈다귀
    BONE_NOTFOUND(HttpStatus.NOT_FOUND, "BONE404", "후원 가능한 뼈다귀가 없습니다."),
    BONE_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "BONE400", "뼈다귀가 충분하지 않습니다."),
    // 후원 공고글
    DONATION_NOTFOUND(HttpStatus.NOT_FOUND, "DONATION404", "해당되는 후원 공고가 없습니다."),
    DONATIONLIST_NOTFOUND(HttpStatus.NOT_FOUND, "DONATION404", "해당되는 후원 내역이 없습니다."),
    DONATION_INVALID(HttpStatus.NOT_FOUND, "DONATION404", "해당 후원 공고는 마감되었습니다."),
    DONATION_OVERFLOW(HttpStatus.BAD_REQUEST, "DONATION400", "목표 후원 금액 미만으로 후원바랍니다."),
    ALREADY_ACTIVE_DONATION(HttpStatus.BAD_REQUEST, "DONATION400", "해당 펫과 관련된 후원 공고가 이미 존재합니다."),

    // 입양
    ALEADY_ACTIVE_ADOPTION(HttpStatus.BAD_REQUEST, "ADOPTION400", "해당 펫과 관련된 입양 공고가 이미 존재합니다."),
    ADOPTION_NOTFOUND(HttpStatus.NOT_FOUND, "ADOPTION404", "해당되는 입양 공고가 없습니다."),
    ADOPTION_ADOPTING(HttpStatus.BAD_REQUEST, "ADOPTION400", "입양 절차 진행중입니다."),
    ADOPTION_COMPLETED(HttpStatus.BAD_REQUEST, "ADOPTION400", "입양 완료된 공고입니다."),
    ADOPTION_BAD_REQUEST(HttpStatus.BAD_REQUEST, "ADOPTION400", "자기 자신이 입양할 수 없습니다.");




    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
