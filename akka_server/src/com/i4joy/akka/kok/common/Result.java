/**
 * 
 */
package com.i4joy.akka.kok.common;

/**
 * @author Administrator
 *
 */
public class Result<T> {
	
	private boolean isSuccess;
	
	private String result;
	
	private int errorCode;
	
	private T[] objs;

	/**
	 * 
	 */
	public Result() {
		
	}
	
	public Result(boolean isSuccess){
		this.isSuccess=isSuccess;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public T[] getObjs() {
		return objs;
	}

	public void setObjs(T[] objs) {
		this.objs = objs;
	}

}
