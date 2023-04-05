package com.youn.intly.exception;

import com.youn.intly.exception.http.ExternalException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum ErrorType {

    X001("X001", "정의되지 않은 에러 입니다.", UndefinedException.class)
    ;


    private static final Map<Class<? extends IntlyException>, ErrorType> ERROR_CODE = new HashMap<>();

    private final String code;
    private final String message;
    private final Class<? extends IntlyException> classType;

    ErrorType(String code, String message, Class<? extends IntlyException> classType) {
        this.code = code;
        this.message = message;
        this.classType = classType;
    }

    static {
        Arrays.stream(values())
                .filter(ErrorType::isInternalException)
                .forEach(errorType -> ERROR_CODE.put(errorType.classType, errorType));
    }

    public static ErrorType of(Class<? extends IntlyException> classType) {
        if (classType.equals(ExternalException.class)) {
            throw new UnsupportedOperationException();
        }
        return ERROR_CODE.getOrDefault(classType, X001);
    }

    public static ErrorType of(String code) {
        return Arrays.stream(values())
                .parallel()
                .filter(errorType -> errorType.isSameCode(code))
                .findAny()
                .orElse(X001);
    }

    private boolean isInternalException() {
        return !classType.equals(ExternalException.class);
    }

    private boolean isSameCode(String code) {
        return Objects.equals(this.code, code);
    }
}