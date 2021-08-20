package com.yusys.agile.config;


import com.yusys.portal.model.common.dto.ControllerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理
 *
 * @create 2021/2/1
 */
@ControllerAdvice
public class MyExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);

    /**
     * 功能描述: 处理全局的异常：代码中未进行trycatch处理的异常
     *
     * @param e
     * @return
     * @date 2021/2/1
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ControllerResponse exceptionHandler(Exception e) {
        LOGGER.error("全局异常处理 BusinessException: {} ", e.getMessage());
        return ControllerResponse.fail(e.getMessage());
    }


}