package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Wrong;

import java.util.List;

public interface WrongRepository {

    // userID 별로 오답문제 전체 조회
    List<Question> findAll(String user_id);

    // 틀린문제 랜덤으로 하나씩 조회
    List<Question> loadNext();

    List<Question> loadPrev();

    // 틀린문제 풀어보고 맞은 경우 테이블에서 삭제
    void updateWrong(int question_id);

}
