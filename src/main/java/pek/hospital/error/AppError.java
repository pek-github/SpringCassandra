package pek.hospital.error;


public enum AppError {

    INVALID_PARAMETER(1, "Input parameter is not valid"),
    INVALID_PARAMETER_COMBINATION(2, "Input parameters combination is not valid"),

    REQUESTED_RESOURCE_DOES_NOT_EXIST(101, "Requested resource does not exist in the database"),

    UNEXPECTED_ERROR(999, "Unexpected error occurred");


    private final Integer code;
    private final String message;


    AppError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public final Integer getCode() {
        return code;
    }

    public final String getMessage() {
        return message;
    }

}
