package viosmash.core.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import viosmash.core.exception.ServiceException;
import viosmash.core.exception.ServiceExceptionEnum;
import viosmash.core.vo.CommonResult;

@ControllerAdvice(basePackages = "viosmash.controller")
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult serviceExceptionHandler(HttpServletRequest req, ServiceException ex) {
        logger.debug("[serviceExceptionHandler]", ex);
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResult constraintViolationExceptionHandler(HttpServletRequest req, ConstraintViolationException ex) {
        logger.debug("[constraintViolationExceptionHandler]", ex);
        StringBuilder detailMessage = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            // 使用 ; 分隔多个错误
            if (detailMessage.length() > 0) {
                detailMessage.append(";");
            }
            detailMessage.append(constraintViolation.getMessage());
        }
        return CommonResult.error(ServiceExceptionEnum.INVALID_REQUEST_PARAM_ERROR.getCode(),
                ServiceExceptionEnum.INVALID_REQUEST_PARAM_ERROR.getMessage() + ": " + detailMessage.toString());
    }
}
