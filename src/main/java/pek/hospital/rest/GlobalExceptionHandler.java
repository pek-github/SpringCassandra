package pek.hospital.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pek.hospital.error.AppError;
import pek.hospital.error.AppException;
import pek.hospital.error.ResourceNotFoundException;
import pek.hospital.rest.dto.ErrorResponseDto;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> resourceNotFoundExceptionHandler(ResourceNotFoundException resourceNotFoundException) {
        ErrorResponseDto errorResponseDto
            = createErrorResponseDto(resourceNotFoundException.getError(), resourceNotFoundException.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponseDto> applicationExceptionHandler(AppException appException) {
        ErrorResponseDto errorResponseDto = createErrorResponseDto(appException.getError(), appException.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> allExceptionHandler(Exception ex) {
        ErrorResponseDto errorResponseDto = createErrorResponseDto(AppError.UNEXPECTED_ERROR, ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ErrorResponseDto createErrorResponseDto(AppError appError, String details) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setCode(appError.getCode());
        errorResponseDto.setMessage(appError.getMessage());
        errorResponseDto.setDetails(details);
        return errorResponseDto;
    }

}
