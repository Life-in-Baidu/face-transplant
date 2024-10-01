package com.ruoyi.biz.api.enume;

import lombok.Getter;

/**
 * @Author xph
 * @Date 2022/7/14 14:24
 * @Description: 用户登录注册,错误码和错误信息定义类
 *
 * 错误码列表：
 *      10：注册
 *          001：重复注册
 *
 */
@Getter
public enum BizLoginEnume {
    REPEAT_REG(10001,"账号已存在");

    private int code;
    private String msg;
    BizLoginEnume(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
