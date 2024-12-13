package viosmash.core.exception;

public enum ServiceExceptionEnum {
    SUCCESS(0, "Thanh cong"),
    SYS_ERROR(2001001000, "Loi he thong"),
    MISSING_REQUEST_PARAM_ERROR(2001001001, "Tham so truyen vao bi thieu"),
    INVALID_REQUEST_PARAM_ERROR(2001001002, "Tham so khong hop le"),
    USER_NOT_FOUND(1001002000, "Khong tim thay user");
    private final int code;
    private final String message;

    ServiceExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
