package  team.CowsAndHorses.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import team.CowsAndHorses.config.ProfileConfig;
import team.CowsAndHorses.dto.AjaxResult;
import team.CowsAndHorses.dto.ErrorDetail;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;

/**
 * @author patrick_star
 * @Desc 处理参数校验相关异常
 * @version 1.0
 */
@Order(10)
@ControllerAdvice
@RequiredArgsConstructor
public class ValidateExceptionHandler {
    final ProfileConfig profileConfig;

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public AjaxResult<Object> handleSaTokenException(ConstraintViolationException e, HttpServletRequest request) {
        ErrorDetail errorDetail;
        if (profileConfig.isDev()) {
            errorDetail = ErrorDetail.builder()
                    .requestId(request.getAttribute("requestId").toString())
                    .message(e.getMessage()).path(request.getRequestURI())
                    .timestamp(Instant.now()).build();
        } else {
            errorDetail = ErrorDetail.builder()
                    .timestamp(Instant.now()).build();
        }

        return AjaxResult.FAIL("格式校验错误", errorDetail);
    }

    /**
     * @Desc 参数校验错误拦截处理
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public AjaxResult<Object> validationBodyException(MethodArgumentNotValidException exception, HttpServletRequest request){
        ErrorDetail errorDetail;
        if (profileConfig.isDev()) {
            errorDetail = ErrorDetail.builder()
                    .requestId(request.getAttribute("requestId").toString())
                    .message(exception.getMessage()).path(request.getRequestURI())
                    .timestamp(Instant.now()).build();
        } else {
            errorDetail = ErrorDetail.builder()
                    .timestamp(Instant.now()).build();
        }

        return AjaxResult.FAIL("参数校验错误", errorDetail);
    }

    /**
     * @Desc SQL执行失败错误（重复主键）拦截处理
     * @param exception 错误信息集合
     * @return 错误信息
     */

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public AjaxResult<Object> validationBodyException(SQLIntegrityConstraintViolationException exception, HttpServletRequest request){
        ErrorDetail errorDetail;
        if (profileConfig.isDev()) {
            errorDetail = ErrorDetail.builder()
                    .requestId(request.getAttribute("requestId").toString())
                    .message(exception.getMessage()).path(request.getRequestURI())
                    .timestamp(Instant.now()).build();
        } else {
            errorDetail = ErrorDetail.builder()
                    .timestamp(Instant.now()).build();
        }

        return AjaxResult.FAIL("SQL执行失败，请检查是否字段填写错误", errorDetail);
    }

    /**
     * @Desc Json解析失败错误（字段填写错误或漏填必选值）拦截处理
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(JsonMappingException.class)
    @ResponseBody
    public AjaxResult<Object> validationBodyException(JsonMappingException exception, HttpServletRequest request){
        ErrorDetail errorDetail;
        if (profileConfig.isDev()) {
            errorDetail = ErrorDetail.builder()
                    .requestId(request.getAttribute("requestId").toString())
                    .message(exception.getMessage()).path(request.getRequestURI())
                    .timestamp(Instant.now()).build();
        } else {
            errorDetail = ErrorDetail.builder()
                    .timestamp(Instant.now()).build();
        }

        return AjaxResult.FAIL("Json解析失败，" , errorDetail);
    }

}
