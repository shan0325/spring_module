package com.sm.app.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.FieldError;

import java.util.List;

@AllArgsConstructor
@ToString
@Getter
public class ExceptionResponse {
    private StatusCode statusCode;
    private String code;
    private String message;
    private String redirectUrl;
    private List<FieldError> fieldErrors;

    public static ExceptionResponseBuilder builder(String message) {
        return innerBuilder()
                .httpStatus(StatusCode.INTERNAL_SERVER_ERROR)
                .code(String.valueOf(StatusCode.INTERNAL_SERVER_ERROR.value()))
                .message(message);
    }

    public static ExceptionResponseBuilder innerBuilder() {
        return new ExceptionResponseBuilder();
    }

    @ToString
    public static class ExceptionResponseBuilder {
        private StatusCode statusCode;
        private String code;
        private String message;
        private String redirectUrl;
        private List<FieldError> fieldErrors;

        public ExceptionResponseBuilder httpStatus(StatusCode statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ExceptionResponseBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ExceptionResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ExceptionResponseBuilder redirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
            return this;
        }

        public ExceptionResponseBuilder fieldErrors(List<FieldError> fieldErrors) {
            this.fieldErrors = fieldErrors;
            return this;
        }

        public ExceptionResponse build() {
            return new ExceptionResponse(this.statusCode, this.code, this.message, this.redirectUrl, this.fieldErrors);
        }
    }
}
