package mgs.training.javaoracle.pelatihanapi.dto.http;

import java.util.Optional;

import mgs.training.javaoracle.pelatihanapi.constant.ErrorCode;

public class HttpRespModel<T> {

	private boolean success;
	private int code; // value of ErrorCode.java
	private String message;
	private int totalRow;
	private T data;

	public HttpRespModel() {}

    public HttpRespModel(T data) {
        this(true, ErrorCode.SUCCESS, "Success", data, 0);
    }

    public HttpRespModel(boolean success, int code, String message, T data, int totalRow) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.totalRow = totalRow;
    }

    public static <T> HttpRespModel<T> ok(T data) {
        return new HttpRespModel<>(true, ErrorCode.SUCCESS, "Success", data, 0);
    }
    public static <T> HttpRespModel<T> ok(T data, int totalRow) {
        return new HttpRespModel<>(true, ErrorCode.SUCCESS, "Success", data, totalRow);
    }

    public static <T> HttpRespModel<T> ok(T data, String message) {
        return new HttpRespModel<>(true, ErrorCode.SUCCESS, message, data, 0);
    }

    public static <T> HttpRespModel<T> error(String message) {
        return error(ErrorCode.FAILED, message, null);
    }

    public static <T> HttpRespModel<T> error(int code, String message) {
        return error(code, message, null);
    }

    public static <T> HttpRespModel<T> error(int code, String message, T data) {
        return new HttpRespModel<>(false, code, message, data, 0);
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(this.data);
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
    
    public int getTotalRow() {
		return totalRow;
	}

	@Override
    public String toString() {
        return "HttpRespModel{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
