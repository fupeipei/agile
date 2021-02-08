package com.yusys.agile.utils.result;


import com.yusys.agile.utils.exception.AgileError;

public class ResultObject {
	private boolean hasError;
	private Object data;
	private AgileError error;
	private int code;

	private ResultObject() {
	}

	private ResultObject(Object data) {
		this.data = data;
	}

	private ResultObject(AgileError error) {
		this.hasError = true;
		this.error = error;
	}

	public static ResultObject success(Object data) {
		return new ResultObject(data);
	}

	public static ResultObject fail(AgileError error) {
		return new ResultObject(error);
	}

	public static ResultObject fail(int code, Object data ) {
		return new ResultObject(code,data);
	}

	public ResultObject(int code,Object data) {
		this.code = code;
		this.data = data;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public AgileError getError() {
		return error;
	}

	public void setError(AgileError error) {
		this.error = error;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
