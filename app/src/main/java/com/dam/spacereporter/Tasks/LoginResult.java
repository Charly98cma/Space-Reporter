package com.dam.spacereporter.Tasks;

public abstract class LoginResult<T> {

    private LoginResult() {}

    public static final class Success<T> extends LoginResult<T> {
        public T data;

        public Success(T data) {
            this.data = data;
        }
    }

    public static final class Error<T> extends LoginResult<T> {
        public Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }
    }
}
