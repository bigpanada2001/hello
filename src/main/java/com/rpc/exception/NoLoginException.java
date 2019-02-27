package com.rpc.exception;

public class NoLoginException extends CommonException {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_ERROR_CODE = 501 ;
	
	public NoLoginException(String message) {
        super(DEFAULT_ERROR_CODE,message);
    }

    public NoLoginException(String message, Throwable c) {
        super(DEFAULT_ERROR_CODE,message, c);
    }

    public NoLoginException(Throwable c) {
        super(DEFAULT_ERROR_CODE,c);
    }
    
}
