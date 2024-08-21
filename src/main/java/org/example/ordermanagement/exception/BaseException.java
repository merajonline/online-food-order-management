package org.example.ordermanagement.exception;

import java.io.Serial;

public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1724137833000L;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
