package com.stoom.addressapi.exception.handler;


import com.stoom.addressapi.exception.CommunicationException;
import com.stoom.addressapi.model.dto.response.ErrorResponse;
import com.stoom.addressapi.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class AddressExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException() {
        return ErrorResponse.as(message("resource.notfound.message"));
    }

    @ExceptionHandler(CommunicationException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleCommunicationException() {
        return ErrorResponse.as(message("communication-failed.message"));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {

        List<ErrorResponse> errors = ex.getBindingResult().getAllErrors().stream().map(violation -> {
            String description = message(violation);
            return ErrorResponse.as(description).tag(description);
        }).collect(Collectors.toList());

        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    private String message(final ObjectError violation) {
        return messageSource.getMessage(violation, LocaleContextHolder.getLocale());
    }

    private String message(final String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

}
