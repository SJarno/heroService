package com.sjarno.heroesservice.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom class to handle exceptions. Overrides are last, custom methods first.
 * https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Custom method to handle missing hero
     * 
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(HeroNotFoundException.class)
    protected ResponseEntity<Object> handleHeroNotFoundException(
            HeroNotFoundException ex,
            WebRequest request) {

        List<String> errors = new ArrayList<>() {
            {
                add(ex.getLocalizedMessage());

            }
        };
        String requestDescription = request.toString();

        String message = String.format("%s", ex.getMessage());
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND,
                ex.getClientErrorName(),
                requestDescription,
                message,
                errors);

        return handleExceptionInternal(ex, apiError,
                new HttpHeaders(), apiError.getStatus(), request);
    }

    /**
     * Checks for typemismatches in pathvariables
     * 
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        // TODO: check for nullpointer
        String errorMessage = ex.getName() + " should be type of " + ex.getRequiredType().getName();
        List<String> errors = new ArrayList<>() {
            {
                add(errorMessage);
            }
        };

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Hero service error",
                request.toString(),
                ex.getLocalizedMessage(),
                errors);

        return handleExceptionInternal(ex, apiError,
                new HttpHeaders(), apiError.getStatus(), request);

    }

    /* Handle all exception types */
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Hero service error",
                request.toString(),
                ex.getLocalizedMessage(),
                new ArrayList<>() {
                    {
                        add("Fallback internal error");
                    }
                });
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    /* Database errors? */
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>() {
            {
                add(ex.getLocalizedMessage());

            }
        };
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                ex.getClass().toString(),
                "Binding exception!",
                "",
                errors);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    /* When validations fail - @Valid */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errors.add(e.getField() + ":" + e.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors()
                .forEach(e -> errors.add(e.getObjectName() + ":" + e.getDefaultMessage()));
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Hero service error",
                request.toString(),
                ex.getMessage(),
                errors);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>() {
            {
                add(ex.getLocalizedMessage());

            }
        };
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                ex.getClass().toString(),
                "handleMissingServletRequestPart",
                "",
                errors);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);

    }

    /**
     * For missing request params
      */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>() {
            {
                add(ex.getLocalizedMessage());

            }
        };
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Hero Service Exception == "+ex.getClass().toGenericString(),
                request.toString(),
                ex.getMessage(),
                errors);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);

    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
            HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        // TODO Auto-generated method stub
        return super.handleAsyncRequestTimeoutException(ex, headers, status, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleConversionNotSupported(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleHttpMediaTypeNotAcceptable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleHttpMediaTypeNotSupported(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleHttpMessageNotWritable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                ex.getClass().toString(),
                "KVAAK",
                "",
                null);

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleNoHandlerFoundException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // TODO Auto-generated method stub
        return super.handleServletRequestBindingException(ex, headers, status, request);
    }

}
