package com.ixilink.banknote_box.common.exception;

import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseBody
	@ExceptionHandler(value = BusinessException.class)
	public Result exceptionHandler(BusinessException e) {
		log.error("错误信息：" + e);
		try {
			int code = 444;
			String message = "";
			Field[] f = e.getClass().getDeclaredFields();
			for (Field aF : f) {
				aF.setAccessible(true);
				if (aF.getName().equals("code")) {
					code = (int)aF.get(e);
					continue;
				}
				if (aF.getName().equals("errorMessage")) {
					message = aF.get(e).toString();
				}
			}
			return new Result(code, message);
		} catch (Exception e2) {
			return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
		}
	}

	@ResponseBody
	@ExceptionHandler(value = MaxUploadSizeExceededException.class)
	public Result uploadException(HttpServletRequest httpServletRequest, MaxUploadSizeExceededException e) {
		try {
			return new Result(Code.PARAMETER_ERROR.getCode(), "文件过大");
		}catch (Exception e2) {
			return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
		}
	}
	@ResponseBody
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public Result parameterException(HttpServletRequest httpServletRequest, MissingServletRequestParameterException e) {
		try {
			return new Result(Code.PARAMETER_ERROR.getCode(), Code.PARAMETER_ERROR.getMessage());
		}catch (Exception e2) {
			return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
		}
	}

	@ResponseBody
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public Result param(HttpServletRequest httpServletRequest) {
		try {
			return new Result(Code.PARAMETER_ERROR.getCode(), Code.PARAMETER_ERROR.getMessage());
		}catch (Exception e) {
			return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
		}
	}

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public Result exception(HttpServletRequest httpServletRequest, Exception e) {
		log.error("controller---未知异常", e);
		return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
	}

}
