package com.post.zybx.common;

/**
 * @author itle
 * @version 1.0
 * @date 2020/11/13
 */
public class CommonResult<T> {

    private static final Integer DEFAULT_SUCCESS_STATUS = 200;
    private static final Integer DEFAULT_FAIL_STATUS = 500;
    private static final String DEFAULT_SUCCESS_MSG = "成功";
    private static final String DEFAULT_FAIL_MSG = "系统出错";

    private T Data;
    private Integer status;
    private String msg;


    public CommonResult(T data, Integer status, String msg) {
        Data = data;
        this.status = status;
        this.msg = msg;
    }

    /**
     * 成功返回消息
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonResult success(T data){
        return  new CommonResult(data,DEFAULT_SUCCESS_STATUS,DEFAULT_SUCCESS_MSG);
    }

    /**
     * 返回失败
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonResult fail(T data){
        return  new CommonResult(data,DEFAULT_FAIL_STATUS,DEFAULT_FAIL_MSG);
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
