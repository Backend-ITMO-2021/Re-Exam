package com.sb.ifmo.reexam.requests;

import java.time.LocalDate;


public class AllMessagesRequest {
    private LocalDate from;
    private LocalDate to;

    public LocalDate getFrom() {
        if (from == null) {
            return LocalDate.of(2021, 8, 25);
        }
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        if (to == null) {
            return LocalDate.now();
        }
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
