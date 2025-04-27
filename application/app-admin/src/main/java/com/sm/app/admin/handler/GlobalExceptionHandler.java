package com.sm.app.admin.handler;

import com.sm.app.admin.util.WebUtil;
import com.sm.app.common.exception.ExceptionResponse;
import com.sm.app.common.exception.StatusCode;
import com.sm.app.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * HttpMessageConvert를 사용한 binding 오류 발생시
     * @RequestBody, @RequestPart @Valid 유효성 체크 오류 발생 시
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage(), e);

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        ExceptionResponse exceptionResponse = ExceptionResponse.builder(fieldErrors.get(0).getDefaultMessage())
                .code(String.valueOf(StatusCode.BAD_REQUEST.value()))
                .httpStatus(StatusCode.BAD_REQUEST)
                .fieldErrors(fieldErrors)
                .build();

        return createModelAndView(exceptionResponse, request, response);
    }

    /**
     * @ModelAttribute @Valid 유효성 체크 오류 발생 시
     */
    @ExceptionHandler(BindException.class)
    public ModelAndView handleBindException(BindException e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage(), e);

        List<FieldError> fieldErrors = e.getFieldErrors();
        ExceptionResponse exceptionResponse = ExceptionResponse.builder(fieldErrors.get(0).getDefaultMessage())
                .code(String.valueOf(StatusCode.BAD_REQUEST.value()))
                .httpStatus(StatusCode.BAD_REQUEST)
                .fieldErrors(fieldErrors)
                .build();

        return createModelAndView(exceptionResponse, request, response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage(), e);

        ExceptionResponse exceptionResponse = ExceptionResponse.builder("접근 권한이 없습니다.")
                .code(String.valueOf(StatusCode.FORBIDDEN.value()))
                .httpStatus(StatusCode.FORBIDDEN)
                .build();

        return createModelAndView(exceptionResponse, request, response);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage(), e);

        ExceptionResponse exceptionResponse = ExceptionResponse.builder("시스템 오류가 발생하였습니다. 다시 시도해 주세요").build();
        return createModelAndView(exceptionResponse, request, response);
    }

    @ExceptionHandler(ServiceException.class)
    public ModelAndView handleServiceExceptionException(ServiceException e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage(), e);

        return createModelAndView(e.getExceptionResponse(), request, response);
    }

    private ModelAndView createModelAndView(ExceptionResponse exceptionResponse, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        if (WebUtil.isAjaxRequest(request)) { // ajax인 경우
            mav.setStatus(HttpStatus.valueOf(exceptionResponse.getStatusCode().value()));
            mav.addObject("exceptionResponse", exceptionResponse);
            mav.setViewName("jsonView");
        } else {
            mav.addObject("exceptionResponse", exceptionResponse);
            mav.setViewName("pages/error/error");
        }

        return mav;
    }
}
