package com.ixilink.banknote_box.common.exception;

/**
 * @ClassName: BusinessException
 * @Description: 全局业务层异常处理类
 * @author xiadx
 * @date 2019-3-6 上午16:00:00
 * @version 1.0
 */
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3495093429697403673L;

	private Integer code;
	private String errorMessage;

	public BusinessException(Integer code, String errorMessage) {
		super();
		this.code = code;
		this.errorMessage = errorMessage;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "BusinessException [code=" + code + ", errorMessage=" + errorMessage + "]";
	}

}
