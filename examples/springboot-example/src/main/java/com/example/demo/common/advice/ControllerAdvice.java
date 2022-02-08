package com.example.demo.common.advice;

import com.example.demo.common.exception.CommonException;
import com.example.demo.common.i18n.CommonError;
import com.example.demo.common.response.CommonResponse;
import com.example.demo.common.response.ValidationFailure;
import com.example.demo.common.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author humbinal
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * BindException
     *
     * @param e BindException
     * @return CommonResponse
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BindException.class)
    public CommonResponse<?> handle(BindException e) {
        log.error("ControllerAdvice handled BindException, for ", e);
        BindingResult bindingResult = e.getBindingResult();
        List<ValidationFailure> validationFailures = bindingResult.getAllErrors().stream().map(objectError -> {
            String objectName = objectError.getObjectName();
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                objectName = fieldError.getField();
            }
            return new ValidationFailure(objectName, objectError.getDefaultMessage());
        }).collect(Collectors.toList());
        return ResponseUtils.error(CommonError.INVALID_PARAMS, validationFailures);
    }


    /**
     * CommonException
     *
     * @param e Throwable
     * @return CommonResponse
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CommonException.class)
    public CommonResponse<?> handle(CommonException e) {
        log.error("ControllerAdvice handled CommonException, for ", e);
        return ResponseUtils.error(e.getCode());
    }

    /**
     * INTERNAL_SERVER_ERROR
     *
     * @param e Throwable
     * @return CommonResponse
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public CommonResponse<?> handle(Throwable e) {
        log.error("ControllerAdvice handled Throwable, for ", e);
        return ResponseUtils.error(CommonError.INTERNAL_SERVER_ERROR);
    }
}
