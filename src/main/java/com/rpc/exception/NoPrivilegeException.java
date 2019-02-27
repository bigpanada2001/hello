package com.rpc.exception;

public class NoPrivilegeException extends CommonException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8247156935754711275L;
	public static final int DEFAULT_ERROR_CODE = 504 ;

    public NoPrivilegeException(String message) {
        super(DEFAULT_ERROR_CODE,message);
    }

    public NoPrivilegeException(String message, Throwable c) {
        super(DEFAULT_ERROR_CODE,message, c);
    }

    public NoPrivilegeException(Throwable c) {
        super(DEFAULT_ERROR_CODE,c);
    }
}
