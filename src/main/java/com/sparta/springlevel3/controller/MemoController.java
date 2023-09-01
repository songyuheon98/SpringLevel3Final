package com.sparta.springlevel3.controller;

import com.sparta.springlevel3.dto.MemoCommentResponseAllDto;
import com.sparta.springlevel3.dto.MemoRequestDto;
import com.sparta.springlevel3.dto.MemoResponseDto;
import com.sparta.springlevel3.entity.Memo;
import com.sparta.springlevel3.entity.User;
import com.sparta.springlevel3.service.MemoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {
    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        return memoService.createMemo(requestDto, user.getUsername());
    }

    @GetMapping("/memos")
    public List<MemoCommentResponseAllDto> getMemos() {
        return memoService.getMemos();
    }

    @GetMapping("/memos/{id}")
    public MemoCommentResponseAllDto getOneMemo(@PathVariable Long id) {
        return memoService.getOneMemo(id);
    }

    @PutMapping("/memos/{id}")
    public Memo updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        return memoService.updateMemo(id, requestDto, user.getUsername(), user.getRole());
    }

    @DeleteMapping("/memos/{id}")
    public ResponseEntity<String> deleteMemo(@PathVariable Long id, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        return memoService.deleteMemo(id, user.getUsername(), user.getRole());
    }
}