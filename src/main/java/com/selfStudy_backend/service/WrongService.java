package com.selfStudy_backend.service;

import com.selfStudy_backend.domain.Question;

import java.util.List;

public interface WrongService {

    // userID별 오답 문제 전체 조회
    List<Question> findAllQuestion(String user_id);

    // 오답 문제 풀이
    String solve(List<Question> questionList, String inputAnswer);



}
