package com.naruse.shopping.common.base.result;

import com.naruse.shopping.common.base.enums.StateCodeEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ResultObject<T> implements Serializable {
    /** 状态码 **/
    private int code;
    /** 提示信息 **/
    private String msg;
    /** 返回对象 **/
    private T data;

    public static ResultObject.ResultObjectBuilder getSuccessBuilder() {
        return ResultObject.builder()
                .code(StateCodeEnum.SUCCESS.getCode())
                .msg(StateCodeEnum.SUCCESS.getMsg());
    }

    public static ResultObject.ResultObjectBuilder getFailedBuilder() {
        return ResultObject.builder()
                .code(StateCodeEnum.FAILED.getCode())
                .msg(StateCodeEnum.FAILED.getMsg());
    }
}
