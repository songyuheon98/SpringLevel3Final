package com.sparta.springlevel3.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OnlyMemo {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contents;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime modifiedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public OnlyMemo(Memo memo) {
        this.username = memo.getUsername();
        this.title = memo.getTitle();
        this.contents = memo.getContents();
        this.createdAt = memo.getCreatedAt();
        this.modifiedAt = memo.getModifiedAt();
    }

    public OnlyMemo(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
