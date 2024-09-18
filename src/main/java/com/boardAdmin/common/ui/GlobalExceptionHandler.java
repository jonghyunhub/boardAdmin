package com.boardAdmin.common.ui;


import com.boardAdmin.common.exception.DomainException;
import com.boardAdmin.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    @ExceptionHandler(AuthException.class)
//    protected ResponseEntity<ResponseContainer<ErrorResponse>> handleAuthException(final AuthException e, HttpServletRequest request) {
//        logErrorDetails("handleAuthException", e, request);
//        final ErrorCode errorCode = e.getErrorCode();
//        final ErrorResponse response = ErrorResponse.of(errorCode);
//        return new ResponseEntity<>(
//                ResponseContainer.fail(response),
//                HttpStatus.valueOf(response.getStatus())
//        );
//    }


    @ExceptionHandler(DomainException.class)
    protected ResponseEntity<ResponseContainer<ErrorResponse>> handleBusinessException(final DomainException e, HttpServletRequest request) {
        logErrorDetails("handleBusinessException", e, request);
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(
                ResponseContainer.fail(response),
                HttpStatus.valueOf(errorCode.getStatus())
        );
    }

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ResponseContainer<ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        logErrorDetails("handle MethodArgumentNotValidException", e.getMessage(), request);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(
                ResponseContainer.fail(response),
                HttpStatus.BAD_REQUEST
        );
    }


    /**
     * @ModelAttribute 에서 binding 실패하면 BindException 발생.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ResponseContainer<ErrorResponse>> handleBindException(BindException e, HttpServletRequest request) {
        logErrorDetails("handle BindException", e, request);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(
                ResponseContainer.fail(response),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ResponseContainer<ErrorResponse>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        logErrorDetails("handle HttpRequestMethodNotSupportedException", e, request);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(
                ResponseContainer.fail(response),
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseContainer<ErrorResponse>> handleException(Exception e, HttpServletRequest request) {
        logErrorDetails("handle EntityNotFoundException", e, request);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(
                ResponseContainer.error(response),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private void logErrorDetails( String logMessage, Exception e, HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        String httpMethod = request.getMethod();
        String requestUri = request.getRequestURI();
        log.error("Error occurred for request [method: {}, URI: {}, client IP: {}]", httpMethod, requestUri, clientIp);
        log.error(logMessage, e);
    }

    private void logErrorDetails( String logMessage, String errormessage, HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        String httpMethod = request.getMethod();
        String requestUri = request.getRequestURI();
        log.error("Error occurred for request [method: {}, URI: {}, client IP: {}]", httpMethod, requestUri, clientIp);
        log.error(logMessage, errormessage);
    }

}
