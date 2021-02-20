/**
 * 
 */
package top.anets.utils;

/**
 * @author Administrator
 *
 */
public class Result {
	public Boolean success; //成功还是失败
	public String code  = "406";      //指失败的状态码 
	public String msg;    
	public Object data;    
    /**
	 * 
	 */
	
	public Result(Boolean success,String code,String msg,Object data) {
		this.success=success;
		this.code=code;
		this.msg=msg;
		this.data=data;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Result( ) { 
	}
	
	
	public Result(Boolean success,Status status,Object data) {
		this.success=success;
		if(status!=null) {
			this.code=status.code();
			this.msg=status.msg();
		}else {
			this.code=null;
			this.msg=null;
		}
		this.data=data;
	}
	
	
	
	
	public static Result Success() {
    	return new Result(true,null, null);
    }
    
	
    public static Result Success(Object data) {
    	return new Result(true,null, data);
    }
    
    public static Result Success(String msg ) {
    	return new Result(true,null,msg, null);
    }
    
    public static Result Success(String msg ,Object data) {
    	return new Result(true,null,msg, data);
    }
    
    
    public static Result Success(String code ,String msg ,Object data) {
    	return new Result(true,code,msg, data);
    }
    
    
    public static Result Error() {
    	return new Result(false,null, null);
    }
    
	
    public static Result  Error(Object data) {
    	return new Result(false,null, data);
    }
    
    public static Result  Error(String msg ,Object data) {
    	return new Result(false,null,msg, data);
    }
    
    public static Result  Error( String code ,String msg ,Object data) {
    	return new Result(false,code,msg, data);
    }
    
    public static Result Error(String msg ) {
    	return new Result(false,"500",msg, null);
    }
    





	@Override
	public String toString() {
		return "Result [success=" + success + ", code=" + code + ", msg=" + msg + ", data=" + data + "]";
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


	public Object getData() {
		return data;
	}


	public void setData(Object data) {
		this.data = data;
	}
    
	
	
    
    
    
}
