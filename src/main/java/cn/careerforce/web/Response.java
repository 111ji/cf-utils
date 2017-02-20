package cn.careerforce.web;

import cn.careerforce.config.Global;

public class Response implements java.io.Serializable
{
    private static final long serialVersionUID = -412095459773582659L;

    public static final String MESSAGE_UNLOGED = "unloged";
    public static final String MESSAGE_UNAUTHENTICATED = "unauthenticated";
    public static final String MESSAGE_EXCEPTION = "操作失败，请检查您的输入，或稍后再试！";

    private int result;
    private String message;
    private Object data;

    public Response()
    {
        init();
    }

    public Response(Object data)
    {
        result = 0;
        message = "Success";
        this.data = data;
    }

    public Response(String msg, Object data)
    {
        result = 0;
        message = msg;
        this.data = data;
    }

    public Response(int result, Object data)
    {
        this.result = result;
        message = Global.getResultMessage(result);
        this.data = data;
    }

    public Response(int res, String msg, Object data)
    {
        result = res;
        message = msg;
        this.data = data;
    }

    private void init()
    {
        result = 0;
        message = "";
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "Response [result=" + result + ", message=" + message + ", data=" + data + "]";
    }

}
