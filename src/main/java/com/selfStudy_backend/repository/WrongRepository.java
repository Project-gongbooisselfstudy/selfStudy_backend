package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Wrong;

import java.util.List;

public interface WrongRepository {

    // 오답인 문제만 저장
    void saveWrong(Question question);

    // 전제 오답문제 조회
    List<Wrong> findAll();

    void validateAnswer();



}
