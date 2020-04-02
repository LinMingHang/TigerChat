package com.lmh.utils;

/**
 * @ClassName: TigerChatJSONResult
 * @Description:  自定义响应数据结构
 *                这个类是提供给门户,ios,安卓,微信商城用的
 *  			  门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式(类,或者list)
 *  			  其他自行处理
 *  			  200: 表示成功
 *  			  500: 表示错误,错误信息在msg字段中
 *  			  501: bean验证错误,不管多少个错误都以map形式返回
 *  			  502: 拦截器拦截到用户token出错
 *  			  555: 异常抛出信息
 * @author: ALin
 * @date: 2020/3/29 下午7:38
 */
public class TigerChatJSONResult {
    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    // 不使用
    private String ok;

    public static TigerChatJSONResult build(Integer status, String msg, Object data) {
        return new TigerChatJSONResult(status, msg, data);
    }

    public static TigerChatJSONResult ok(Object data) {
        return new TigerChatJSONResult(data);
    }

    public static TigerChatJSONResult ok() {
        return new TigerChatJSONResult(null);
    }

    public static TigerChatJSONResult errorMsg(String msg) {
        return new TigerChatJSONResult(500, msg, null);
    }

    public static TigerChatJSONResult errorMap(Object data) {
        return new TigerChatJSONResult(501, "error", data);
    }

    public static TigerChatJSONResult errorTokenMsg(String msg) {
        return new TigerChatJSONResult(502, msg, null);
    }

    public static TigerChatJSONResult errorException(String msg) {
        return new TigerChatJSONResult(555, msg, null);
    }

    public TigerChatJSONResult() {

    }

//    public static LeeJSONResult build(Integer status, String msg) {
//        return new LeeJSONResult(status, msg, null);
//    }

    public TigerChatJSONResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public TigerChatJSONResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}