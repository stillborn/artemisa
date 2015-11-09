package com.artemisa.service.exceptions;

public class ExpiredCredentialsException extends Exception {

	private static final long serialVersionUID = 4378436049005301187L;

	public ExpiredCredentialsException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExpiredCredentialsException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public ExpiredCredentialsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ExpiredCredentialsException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ExpiredCredentialsException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
