package com.example.fileeditmulti.common;

/**
 * @author yuwensong
 * @date 2020/5/3
 */
public class Result<T> {


    private String code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode("200");
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failed(T data) {
        Result<T> result = new Result<>();
        result.setCode("400");
        result.setMsg("failed");
        result.setData(data);
        return result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
