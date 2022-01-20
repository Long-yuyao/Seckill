package com.yyy.seckill.result;

public class Result<T> {
    private int code;
    private String msg;
    private T data;


    /*
    成功时候的调用
     */
    public static <T> Result<T> Success(T data){
        return new Result<T>(data);
    }
    /*
    失败时候的调用
     */
    public static <T> Result<T> Fail(CodeMsg msg){
        return new Result<T>(msg);
    }
    public Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }
    public Result(CodeMsg msg) {
        if (msg == null){
            return;
        }
        this.code = msg.getCode();
        this.msg = msg.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
