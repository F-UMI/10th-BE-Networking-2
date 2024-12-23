package cotato.backend.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	//400
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", "COMMON-001"),
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "요청 파라미터가 잘 못 되었습니다.", "COMMON-002"),

	//POST-400
	POST_NOT_FOUND(HttpStatus.NOT_FOUND,"게시물을 찾지 못했습니다." , "COMMON-003" ),
	POST_DELETE_FAILED(HttpStatus.BAD_REQUEST,"게시물 삭제가 실패하였습니다." , "COMMON-004" ),
	POST_SAVE_FAILED(HttpStatus.BAD_REQUEST,"게시물 저장을 실패하였습니다." , "COMMON-005" ),

	//500
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 에러가 발생하였습니다.", "COMMON-002"),
	CONCURRENCY_PROBLEM(HttpStatus.INTERNAL_SERVER_ERROR, "동시성 에러가 발생하였습니다." , "COMMON-003");

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;
}
