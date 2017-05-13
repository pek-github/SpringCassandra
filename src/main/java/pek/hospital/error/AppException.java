package pek.hospital.error;

public class AppException extends Exception {

    private AppError error;

    public AppException(String message, AppError error) {
        super(message);
        this.error = error;
    }

    public final AppError getError() {
        return error;
    }

}
