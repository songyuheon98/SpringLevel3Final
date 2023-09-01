package com.sparta.springlevel3.service;

import com.sparta.springlevel3.dto.*;
import com.sparta.springlevel3.entity.Memo;
import com.sparta.springlevel3.entity.UserRoleEnum;
import com.sparta.springlevel3.repository.CommentRepository;
import com.sparta.springlevel3.repository.MemoRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.LinkedList;
import java.util.List;

@Service// 빈으로 등록
public class MemoService {

    private final MemoRepository memoRepository; // final은 무조건 생성자로 주입
    private final CommentRepository commentRepository;

    private HttpServletResponse httpServletResponse;
    public MemoService(MemoRepository memoRepository, CommentRepository commentRepository) {
        this.memoRepository = memoRepository;
        this.commentRepository = commentRepository;
    }



    public MemoResponseDto createMemo(MemoRequestDto requestDto, String username) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto, username);
        // DB 저장
        Memo saveMemo = memoRepository.save(memo);
        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(saveMemo);
        return memoResponseDto;
    }

    public List<MemoCommentResponseAllDto> getMemos() {
        List<MemoCommentResponseAllDto> memoCommentResponseAllDtoList = new LinkedList<>();
        // DB 조회
        memoRepository.findAllByOrderById().stream() // stream 메소드를 통해 MemoResponseDto로 변환
                .forEach(
                memo -> { // memo MemoResponseDto로 변환
                    MemoCommentDto temp = new MemoCommentDto(memo,
                            commentRepository.findAllByPostidOrderById(memo.getId()).stream()
                                    .map(n->new CommentResponseDto().fromComment(n)).toList());
                    memoCommentResponseAllDtoList.add(new MemoCommentResponseAllDto(temp));

                }

        );// 메모를 리스트 타입으로 반환
        return memoCommentResponseAllDtoList;
    }



    public MemoCommentResponseAllDto getOneMemo(Long id) {
        // DB 조회
        MemoCommentResponseAllDto memoCommentResponseAllDtoList;
        // DB 조회
        Memo tempMemo = memoRepository.findMemoById(id);

        MemoCommentDto temp = new MemoCommentDto(tempMemo,commentRepository.findAllByPostidOrderById(tempMemo.getId()).stream()
                        .map(n->new CommentResponseDto().fromComment(n)).toList());

        memoCommentResponseAllDtoList =new MemoCommentResponseAllDto(temp);
        return memoCommentResponseAllDtoList;
    }

    @Transactional // updateMemo는 따로 Transactional 되어있지 않아 해줘야함
    public Memo updateMemo(Long id, MemoRequestDto requestDto, String username, UserRoleEnum role) {

        Memo memo = findMemo(id);

        httpServletResponse.setContentType("text/html; charset=UTF-8");


        if(role.getAuthority().equals("ROLE_ADMIN")|| memo.getUsername().equals(username) )
            memo.update(requestDto, memo.getUsername()); // update는 memo 클래스에서 만든 것
        else
            throw new IllegalArgumentException("당신에겐 글을 수정할 권한이 없습니다 >.< !!");


        return memo;
    }

    public ResponseEntity<String>  deleteMemo(Long id, String username, UserRoleEnum role) {
        Memo memo = findMemo(id);
        if(role.getAuthority().equals("ROLE_ADMIN")|| memo.getUsername().equals(username))
            memoRepository.delete(memo);
        else
            return ResponseEntity.ok("{\"msg\": \"삭제 실패\", \"statusCode\": 444}");
        return ResponseEntity.ok("{\"msg\": \"삭제 성공\", \"statusCode\": 200}");

    }

    private Memo findMemo(Long id) { // 메모 찾기
        return memoRepository.findById(id).orElseThrow(() ->  // null 시 오류 메시지 출력
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }



}