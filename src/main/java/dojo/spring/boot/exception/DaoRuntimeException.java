package dojo.spring.boot.exception;

public class DaoRuntimeException extends RuntimeException {

    public DaoRuntimeException(Throwable cause) {
        super(cause);
    }
}
