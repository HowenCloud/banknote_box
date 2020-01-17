package com.ixilink.banknote_box.common.model;

/**
 * description:
 * author: 张俊
 * date: 2019-11-05 14:32
 */
public enum Code {
    /**成功*/
    SUCCESS(200,"成功"),
    /** 上传失败 */
    UP_DEFEATED(405,"文件上传失败"),
    /**该账户已被冻结*/
    LOGIN_USER_STATE(408,"该账户已被冻结"),
    /**用户不存在*/
    NO_LOGIN_USER(409,"用户不存在"),
    /** 参数错误 */
    PARAMETER_ERROR(410,"参数错误"),
    /** 登录超时 */
    LONGIN_TIMEOUT(411,"登录超时"),
    /**登录失败*/
    LONGIN_FAIL(412,"用户名或密码错误"),
    /**登出失败*/
    LOGOUT_FAIL(413,"登出失败"),
    /** 验证失败 */
    VERIFY_ERROR(414,"验证失败"),
    /** 验证码错误 */
    VERIFYCode_ERROR(415,"验证码错误"),
    /** 无权访问 */
    NO_SECURITY(416,"无权访问"),
    /** 系统错误 */
    SYSTEM_ERROR(444,"系统错误"),
    /** token信息认证失败 */
    TOKEN_FIND_ERROR(601,"token信息认证失败"),
    /** 提示信息，直接给用户看 */
    MESSAGE_ERROR(666,"提示信息，直接给用户看"),
    /**不能删除，在占用*/
    HOLD_UP(701,"已占用，请等待");

    private Integer code;
    private String message;

    Code(Integer code,String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }
}
