package com.darfik.cloudstorage.web.controller;

import com.darfik.cloudstorage.domain.exception.FileOperationException;
import com.darfik.cloudstorage.domain.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdviser {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleInvalidArgument(MethodArgumentNotValidException exception,
                                        Model model) {
        Map<String, String> errors = new LinkedHashMap<>();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(),
                                error.getDefaultMessage()));

        model.addAttribute("errors", errors);
        return "signup";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException exception,
                                                   Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "signup";
    }

    @ExceptionHandler(FileOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFileUploadException(FileOperationException exception,
                                            Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return ""; //TODO
    }

}
