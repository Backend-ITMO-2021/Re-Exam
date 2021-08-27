package com.dzeru.springchat.dto;

public class ChatterStatDto {
    public String username;
    public Long count;

    public ChatterStatDto() {
    }

    public ChatterStatDto(String username, Long count) {
        this.username = username;
        this.count = count;
    }
}
