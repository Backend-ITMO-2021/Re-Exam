package com.dzeru.springchat.dto;

import java.util.ArrayList;
import java.util.List;

public class TopChattersDto {
    public List<ChatterStatDto> top;

    public TopChattersDto() {
        this.top = new ArrayList<>();
    }

    public TopChattersDto(List<ChatterStatDto> top) {
        this.top = top;
    }

    public void addChatterStatDto(ChatterStatDto chatterStatDto) {
        this.top.add(chatterStatDto);
    }
}
