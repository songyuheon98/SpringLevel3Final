package com.sparta.springlevel3.repository;

import com.sparta.springlevel3.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> { // JpaRepository 덕분에 @Repository 선언 안해도 자동으로 Bean으로 취급
    List<Memo> findAllByOrderById(); // 필요한 정보만 가져오도록 함
    Memo findMemoById(Long id); // 아이디로 메모 찾게 하기
}