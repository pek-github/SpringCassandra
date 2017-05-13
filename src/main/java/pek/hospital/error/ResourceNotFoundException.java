package pek.hospital.error;

public class ResourceNotFoundException extends AppException {

    public ResourceNotFoundException(String message, AppError error) {
        super(message, error);
    }

}
