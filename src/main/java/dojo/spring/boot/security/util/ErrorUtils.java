package dojo.spring.boot.security.util;

import org.springframework.http.HttpStatus;
import dojo.spring.boot.security.model.AuthError;

public class ErrorUtils {

    public static AuthError buildAuthError(String uri, HttpStatus status, String message) {
        AuthError error = new AuthError();
        error.setStatus(status.value());
        error.setError(status.getReasonPhrase());
        error.setMessage(message);
        error.setPath(uri);
        return error;
    }
}
