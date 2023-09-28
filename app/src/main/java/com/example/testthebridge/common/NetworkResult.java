package com.example.testthebridge.common;

public class NetworkResult<T> {
    NetworkResult() {
    }

    public static final class Loading<T> extends NetworkResult<T> {
        public final boolean isLoading;

        public Loading(boolean isLoading) {
            this.isLoading = isLoading;
        }
    }

    public static final class Success<T> extends NetworkResult<T> {
        public final T data;

        public Success(T data) {
            this.data = data;
        }
    }

    public static final class Failure<T> extends NetworkResult<T> {
        public final String errorMessage;

        public Failure(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
