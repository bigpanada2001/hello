package com.rpc.exception;

public class CommonException extends RuntimeException {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_ERROR_CODE = 500 ;
	
	protected int errorCode ;

	public CommonException(String message){
		this(DEFAULT_ERROR_CODE,message);
    }

    public CommonException(String message,Throwable c){
    	this(DEFAULT_ERROR_CODE,message,c);
    }

    public CommonException(Throwable c ){
        this(DEFAULT_ERROR_CODE,c);
    }
    
    public CommonException(int errorCode,String message){
    	super(message);
    	this.errorCode = errorCode ;
    }
    
    public CommonException(int errorCode,String message,Throwable c){
    	super(message,c);
    	this.errorCode = errorCode ;
    }
    
    public CommonException(int errorCode,Throwable c ){
    	super(c);
    	this.errorCode = errorCode ;
    }

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

    
    
}

/*
enum ReturnCode{
    NoLogin(402),NoPrivilege(401),UnKnown(999);


    private int code;

    ReturnCode(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }

}
*/



