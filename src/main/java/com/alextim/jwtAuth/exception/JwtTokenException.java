package com.alextim.jwtAuth.exception;

public class JwtTokenException extends RuntimeException{
    public JwtTokenException(Exception e) {
        super(e);
    }
}
