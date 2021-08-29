package com.chat.domain.dto;

/**
 * @author
 */
public class MessageStatisticDto {
    private final String username;
    private final Long count;

    public MessageStatisticDto(String username, Long count) {
        this.username = username;
        this.count = count;
    }

    public String getUsername() {
        return username;
    }

    public Long getCount() {
        return count;
    }
}
