package com.sparta.springlevel3.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemoCommentResponseAllDto {
    MemoCommentDto postList;
    public MemoCommentResponseAllDto(MemoCommentDto postList){
        this.postList = postList;
    }

}
