package com.yusys.agile.config;

import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.util.Objects;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static String DUPLICATE_KEY_CODE = "500";
    private static String PARAM_FAIL_CODE = "500";
    private static String VALIDATION_CODE = "500";


    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ControllerResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        return new ControllerResponse(PARAM_FAIL_CODE, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(),null);
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public ControllerResponse handleValidationException(ValidationException e) {
        logger.error(e.getMessage(), e);
        return new ControllerResponse(VALIDATION_CODE, e.getCause().getMessage(),null);
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ControllerResponse handleConstraintViolationException(ConstraintViolationException e) {
        logger.error(e.getMessage(), e);
        return new ControllerResponse(PARAM_FAIL_CODE, e.getMessage(),null);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ControllerResponse handlerNoFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        return new ControllerResponse("404", "路径不存在，请检查路径是否正确",null);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ControllerResponse handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return new ControllerResponse(DUPLICATE_KEY_CODE, "数据重复，请检查后提交",null);
    }

}
