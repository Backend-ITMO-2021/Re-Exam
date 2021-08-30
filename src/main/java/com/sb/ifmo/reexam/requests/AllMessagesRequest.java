package com.sb.ifmo.reexam.requests;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class AllMessagesRequest {
    private Date from;
    private Date to;

    public Date getFrom() {
        if (from == null) {
            return new GregorianCalendar(2021, Calendar.AUGUST, 15, 0, 0, 0).getTime();
        }
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        if (to == null) {
            return new Date();
        }
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
