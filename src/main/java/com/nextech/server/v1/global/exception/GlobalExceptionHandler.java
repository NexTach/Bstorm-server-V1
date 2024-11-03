package com.nextech.server.v1.global.exception;

import com.nextech.server.v1.global.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePhoneNumberAlreadyExistsException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, "Phone number already exists");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InvalidJsonFormatException.class, HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleInvalidJsonFormat() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid JSON request format");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ErrorResponse> handleExpiredTokenException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Token has expired");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid token");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJsonFormatException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid token format");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "ID or password does not match");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, "User not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpiredRefreshTokenException.class)
    public ResponseEntity<ErrorResponse> handleExpiredRefreshTokenException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Refresh token has expired");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PhoneNumberNullException.class)
    public ResponseEntity<ErrorResponse> handlePhoneNumberNullException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Phone number is null");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WardMemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWardMemberNotFoundException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, "Ward member not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileUploadFailedException.class)
    public ResponseEntity<ErrorResponse> handleFileUploadException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload file");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RelationNullException.class)
    public ResponseEntity<ErrorResponse> handleRelationNullException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, "You are not the protector in the relationship or the relationship you requested does not exist");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectPasswordException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Incorrect password");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateRoleAssignmentException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRoleAssignmentException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Duplicate role assignment");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFileExtensionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFileExtensionException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid file extension");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingFileNameException.class)
    public ResponseEntity<ErrorResponse> handleMissingFileNameException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Missing file name");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileDeletionFailedException.class)
    public ResponseEntity<ErrorResponse> handleFileDeletionFailedException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete previous profile picture");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MaxFileSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxFileSizeExceededException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "File size exceeds the maximum allowed size of 10MB");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateProfilePictureException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateProfilePictureException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, "Duplicate profile picture");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProfilePictureNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProfilePictureNotFoundException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, "Profile picture not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RelationAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleRelationAlreadyExistsException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, "The relationship with the specified ward already exists.");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRoleException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid role");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRefreshTokenNotFoundException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, "Refresh token not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RefreshTokenDeletionFailedException.class)
    public ResponseEntity<ErrorResponse> handleRefreshTokenDeletionFailedException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete refresh token");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}