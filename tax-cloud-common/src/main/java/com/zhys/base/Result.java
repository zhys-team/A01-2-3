package com.zhys.base;

import java.io.Serializable;


import lombok.Data;

@Data
public class Result implements Serializable {

   private static final long serialVersionUID = -3948389268046368059L;

   private Integer code;

   private String msg;
/**
 * 这里说明下字段data不是在code=1为成功的时候才会有值哦，比如当code为参数无效错误时，data可以放入更详细的错误描述，用于指明具体是哪个参数为什么导致的无效的
 */
   
   private Object data;

   public Result() {}

   public Result(Integer code, String msg) {
       this.code = code;
       this.msg = msg;
   }

   public static Result success() {
       Result result = new Result();
       result.setResultCode(ResultCode.SUCCESS);
       return result;
   }

   public static Result success(Object data) {
       Result result = new Result();
       result.setResultCode(ResultCode.SUCCESS);
       result.setData(data);
       return result;
   }

   public static Result failure(ResultCode resultCode) {
       Result result = new Result();
       result.setResultCode(resultCode);
       return result;
   }

   public static Result failure(ResultCode resultCode, Object data) {
       Result result = new Result();
       result.setResultCode(resultCode);
       result.setData(data);
       return result;
   }

   public void setResultCode(ResultCode code) {
       this.code = code.code();
       this.msg = code.message();
   }
}
