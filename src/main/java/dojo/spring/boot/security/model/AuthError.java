package dojo.spring.boot.security.model;

import lombok.Data;

@Data
public class AuthError {

    private Integer status;
    private String error;
    private String message;
    private String path;

}
