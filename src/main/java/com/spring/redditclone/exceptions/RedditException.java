package com.spring.redditclone.exceptions;

public class RedditException extends RuntimeException {
    public RedditException(String exMessage) {
        super(exMessage);
    }
    public RedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

}
