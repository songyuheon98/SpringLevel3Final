package com.sparta.springlevel3.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.springlevel3.dto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "memo") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Memo extends Timestamped{
    @Id
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "username", nullable = false)
    private String username;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "title", nullable = false)
    private String title;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

//    @Transient
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private String statusCode;
//
//    @Transient
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private String message;



    public Memo(MemoRequestDto requestDto, String tokenUsername) {
        this.username = tokenUsername;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

//    public Memo(String statusCode, String message) {
//        this.statusCode = statusCode;
//        this.message = message;
//    }


    public void update(MemoRequestDto requestDto, String username) {
        this.username = username;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    @OneToMany(mappedBy = "memo", cascade = CascadeType.REMOVE)

    //@JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Comment> comments = new ArrayList<>();
}