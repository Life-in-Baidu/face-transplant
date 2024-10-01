package com.ruoyi.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 操作消息提醒
 *
 * @author ruoyi
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("返回参数")
@Data
public class ApiResult<T>
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="数据")
    private T data;

    @ApiModelProperty(value="状态码")
    private  int code;

    @ApiModelProperty(value="返回提示信息")
    private  String msg;

    /**
     * 初始化一个新创建的 ApiResult 对象，使其表示一个空消息。
     */
    public ApiResult()
    {
    }

    /**
     * 初始化一个新创建的 ApiResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public ApiResult(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 新增实体的返回消息
     * @param rows 影响行数
     * @param <T> 泛型
     * @return 返回结果
     */
    public static <T> ApiResult<T> toAjax(int rows)
    {
        return rows > 0 ? ApiResult.success() : ApiResult.error();
    }
    /**
     * 初始化一个新创建的 ApiResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public ApiResult(int code, String msg, T data)
    {
        this.code = code;
        this.msg = msg;
        if (StringUtils.isNotNull(data))
        {
            this.data = data;
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static <T> ApiResult<T> success()
    {
        return ApiResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static <T> ApiResult<T> success(T data)
    {
        return ApiResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static <T> ApiResult<T> success(String msg)
    {
        return ApiResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> ApiResult<T> success(String msg, T data)
    {
        return new ApiResult<T>(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static <T> ApiResult<T> error()
    {
        return ApiResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static <T> ApiResult<T> error(String msg)
    {
        return ApiResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static <T> ApiResult<T> error(String msg, T data)
    {
        return new ApiResult<T>(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static <T> ApiResult<T> error(int code, String msg)
    {
        return new ApiResult<T>(code, msg, null);
    }

}
