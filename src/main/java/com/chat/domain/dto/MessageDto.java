package com.chat.domain.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.chat.domain.Message;
import com.chat.domain.User;

/**
 * @author
 */
public class MessageDto {
    private Long id;
    private String text;
    private User author;
    private Date date;
    private String stringDate;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.text = message.getText();
        this.author = message.getAuthor();
        this.date = message.getDate();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        stringDate = formatter.format(this.date);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }
    public String getStringDate() {

        return stringDate;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
