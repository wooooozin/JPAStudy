package com.example.jpa.notice.exception;

public class DuplicateNoticeException extends RuntimeException {
    public DuplicateNoticeException(String s) {
        super(s);
    }
}
