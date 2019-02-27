package com.rpc.exception;

public class DataInconsistentException extends CommonException  {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_ERROR_CODE = 503 ;
	
    public DataInconsistentException(String message) {
        super(DEFAULT_ERROR_CODE,message);
    }

    public DataInconsistentException(String message, Throwable c) {
        super(DEFAULT_ERROR_CODE,message, c);
    }

    public DataInconsistentException(Throwable c) {
        super(DEFAULT_ERROR_CODE,c);
    }
}
