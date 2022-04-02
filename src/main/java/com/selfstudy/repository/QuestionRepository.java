package com.selfstudy.repository;

import com.selfstudy.domain.Question;

import java.util.List;

public interface QuestionRepository {

    // 필요한 기능을 인터페이스로 구현

    // 문제 저장
    void saveQuestion(Question question);
    // 문제 조회
    List<Question> findById(int questionId);
    // 문제 수정
    void modifyQuestion(Question question);

    // 삭제 기능도 추가해야함


}
