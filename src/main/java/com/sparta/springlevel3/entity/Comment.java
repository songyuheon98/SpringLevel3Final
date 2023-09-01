package com.sparta.springlevel3.entity;

import com.sparta.springlevel3.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "postid", nullable = false)
    private Long postid;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    // 게시물(Memo)의 id와 Comment의 postId 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postid", referencedColumnName = "id", insertable = false, updatable = false)
    private Memo memo;

//    public MemoResponseDto createComment(CommentRequestDto requestDto, String username) {
//        // RequestDto -> Entity
//        Comment comment = new Comment(requestDto, username);

    public Comment(CommentRequestDto requestDto, String tokenUsername) {
        this.username = tokenUsername;
        this.postid = requestDto.getPostid();
        this.contents = requestDto.getContents();
    }


    public void update(String contents) {
        this.contents = contents;

    }
}
