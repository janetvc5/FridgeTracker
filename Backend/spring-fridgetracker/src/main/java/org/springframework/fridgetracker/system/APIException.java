package org.springframework.fridgetracker.system;

import org.springframework.core.style.ToStringCreator;

public class APIException extends Exception {
	private int errorCode;
	private String errorMessage;
	public APIException(Throwable throwable) {
		super(throwable);
	}
	public APIException(String msg) {
		super(msg);
	}
	public APIException(String msg, Throwable throwa) {
		super(msg,throwa);
	}
	public APIException(int errCode, String errMsg) {
		super();
		this.setErrorCode(errCode);
		this.setErrorMessage(errMsg);
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Override
    public String toString() {
        return new ToStringCreator(this)
        		.append("Error",this.getErrorMessage())
        		.append("Code",this.getErrorCode()).toString();
    }
}
