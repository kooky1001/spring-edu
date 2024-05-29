package hello.exception.exhandler.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ApiExceptionV2Controller;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
// @RestControllerAdvice("hello.exception.exhandler")
// @RestControllerAdvice(assignableTypes = {ApiExceptionV2Controller.class})
public class ExControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ErrorResult illegalExHandle(IllegalArgumentException e) {
		log.error("illegalExHandle", e);
		return new ErrorResult("BAD", e.getMessage());
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResult> userExHandle(UserException e) {
		log.error("userExHandle", e);
		ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
		return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public ErrorResult internalExHandle(Exception e) {
		log.error("internalExHandle", e);
		return new ErrorResult("INTERNAL-EX", "내부 오류");
	}

}
