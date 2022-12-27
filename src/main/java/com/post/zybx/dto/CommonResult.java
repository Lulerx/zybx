package com.post.zybx.dto;

/**
 * @author itle
 * @version 1.0
 * @date 2020/11/13
 */
public class CommonResult<T> {
    private T Data;
    private Integer status;
    private String msg;

    public CommonResult() {
    }

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
        return  new CommonResult(data,200,"成功");
    }

    /**
     * 返回失败
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonResult fail(T data){
        return  new CommonResult(data,500,"系统出错");
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
