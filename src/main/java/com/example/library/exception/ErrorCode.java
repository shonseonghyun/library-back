package com.example.library.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SUC("S00","SUCCESS"),

    //AutehnticationError
    AUTHENTICATION_INSUFFICIENT("A01","로그인 후 접근 바랍니다."),
    AUTHORITY_FAIL("A02","접근할 수 없는 권한입니다. 권한 확인 후 재접근 바랍니다."),

    //User
    USERID_DUPLICATED("U01", "중복 아이디입니다. 아이디를 확인해주세요."),
    USERID_NOT_FOUND("U02", "존재하지 않는 아이디입니다. 아이디를 확인해주세요."),
    PASSWORD_DIFFERNET("U03", "올바르지 않은 패스워드입니다. 패스워드를 확인해주세요."),
    USERNO_NOT_FOUND("U04", "존재하지 않는 유저번호입니다. 유저번호를 확인해주세요."),

    //Heart
    HEARTBOOK_EMPTY("H01","찜한 도서가 없습니다."),
    HEARTNO_NOT_FOUND("H02","존재하지 않는 찜번호입니다. 찜번호를 확인해주세요."),
    HEARTBOOK_ALREADY("H03","이미 찜한 도서입니다."),
    //Book
    BOOKNAME_NOT_FOUND("B01","존재하지 않는 책이름입니다. 다시 확인해주세요."),
    BOOKAUTHOR_NOT_FOUND("B02","존재하지 않는 저자입니다. 다시 확인해주세요."),
    BOOKCODE_NOT_FOUND("B03","존재하지 않는 책번호입니다. 다시 확인해주세요."),
    BOOKSTATE_NOT_FOUND("B04","존재하지 않는 도서 상태 번호 입니다."),
    CATEGORY_NOT_FOUND("B05","존재하지 않는 카테고리입니다."),
    //DateUtil
    DATE_PARSE_ERROR("Y01","날짜 파싱에 실패하였습니다."),


    //Mail
    MAIL_NOT_FOUND("M01", "존재하지 않는 메일주소입니다. 메일주소를 확인해주세요."),
    MAIL_SEND_FAIL("M02", "메일 발송을 실패하였습니다."),
    MAIL_TYPE_NOT_FOUND("M03", "존재하지 않는 메일타입입니다."),

    //Review
    REVIEW_NOT_FOUND("R01", "해당도서의 리뷰가 없습니다."),
    REVIEW_WRITE_AVAILABLE_DUE_TO_NEVER_RENT("R02","대여 히스토리가 존재하지 않아 리뷰작성 불가합니다."),
    REVIEW_ALREADY_WRITE("R03","도서에 대한 완료된 리뷰가 존재합니다."),
    //Parameter
    PARAMETER_FORMAT_INVALID("P01", "인입필드 형식 또는 타입이 올바르지 않습니다. 에러 필드 내 상세 사유를 확인해주세요."),
    PARAMETER_FIELD_INVALID("P02", "JSON 파싱 실패하였습니다. JSON데이터를 확인해주세요."),

    //Rent
    EXCEED_MAXIMUN_RENT_NUMBER("R95","도서 대여 가능 권수 초과하였습니다."),
    RENTMANAGER_USERNO_NOT_FOUND("R96","해당 유저번호에 해당하는 대여매니저가 존재하지 않습니다. 대여 매니저 확인 바랍니다."),
    BOOK_ON_RENT("R97","현재 다른 유저에 의해 대여 중인 도서입니다."),
    OVERDUE_USER("R98","연체 대상자이므로 대여 불가합니다."),
    RENTSTATE_NOT_FOUND("R99","존재하지 않는 렌트상태번호 입니다."),

    //Return
    BOOK_NOT_FOUND_AMONG_BOOKS_ON_RENT("L01","대여 중인 도서 중 해당 도서는 존재하지 않습니다.대여한 책이 맞는지 확인 바랍니다."),

    //File
    UPLOAD_FILE_FAIL("F01","이미지 업로드 실패하였습니다."),

    //
    SEARCH_THROUGHPUT_EXCEEDED_EXCEPTION("S01","서버 접속량이 많습니다. 잠시 후 재시도 부탁드립니다."),

    //Token
    ACCESSTOKEN_EXPIRED("T01","액세스 토큰 유효기간 만료되었습니다. 재로그인 바랍니다."),
    ACCESSTOKEN_PARSE_ERROR("T02","토큰 파싱에 실패하였습니다. 정상적인 토큰이 맞는지 확인 바랍니다."),

    //Extension
    BOOK_EXTEND_NUMBER_EXCEED("E01","도서 연장 횟수 초과하여 연장 불가합니다.")
    ;
    private String code;
    private String msg;
}
