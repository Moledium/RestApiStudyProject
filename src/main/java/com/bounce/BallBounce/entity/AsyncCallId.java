package com.bounce.BallBounce.entity;

public class AsyncCallId {
    private String message;
    private Long futureId;

    public AsyncCallId(String message, Long futureId) {
        this.message = message;
        this.futureId = futureId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getFutureId() {
        return futureId;
    }

    public void setFutureId(Long futureId) {
        this.futureId = futureId;
    }
}
