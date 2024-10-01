package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * 返回结果码定义
 *
 * @author lian.tian
 */
@Getter
public enum ResultCode {

    SUCCESS(0, "成功"),

    PARAM_ERROR(101, "参数错误"),

    RECORD_DUPLICATE(102, "数据重复"),

    RECORD_NOT_EXIST(103, "数据不存在"),

    METHOD_NOT_SUPPORT(104, "HTTP请求方法不支持"),

    LOGIN_FAIL(105, "登入失败"),

    AUTHENTICATION_FAIL(106, "认证失败，用户请登入"),

    AUTHORIZATION_FAIL(107, "权限不够"),

    DATA_CONSTRAINT(108, "数据约束异常"),

    GET_WE_CHAT_SESSION_KEY_FAILED(109, "获取微信SessionKey失败"),

    SIGNATURE_ERROR(110, "签名验证失败"),

    TOKEN_EXPIRED(111, "Token过期"),

    REPO_INSUFFICIENCY(112, "库存不足"),

    ILLEGAL_OPERATION(113,"非法操作"),

    REQUEST_NEW_TOKEN(114,"Token即将过期，请换取新Token"),

    REFRESH_TOKEN_FAIL(115,"刷新Token失败,请重新登入"),

    SYSTEM_ERROR(500, "系统异常"),

    ;
    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
