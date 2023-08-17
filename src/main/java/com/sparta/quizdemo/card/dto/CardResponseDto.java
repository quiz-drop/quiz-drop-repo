package com.sparta.quizdemo.card.dto;

import com.sparta.quizdemo.card.entity.Card;
import lombok.Getter;

@Getter
public class CardResponseDto {
    private Long id;
    private String title;
    private Integer level;
    private String content;
    private String createdAt;
    private String modifiedAt;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.level = card.getLevel();
        this.content = card.getContent();
        this.createdAt = card.getCreatedAtAsString();
        this.modifiedAt = card.getModifiedAtAsString();
    }
}
