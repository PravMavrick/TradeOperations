package com.trade.TradeOperations.Controller.Exception;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException() {
    }

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
