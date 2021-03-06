package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    // 필요한 기능을 인터페이스로 구현

    // 문제 저장
    void saveQuestion(Question question);
    // 전체 문제 조회
//    List<Question> findAll();

    // 회원아이디로 조회
    List<Question> findById(String user_id);

    // 문제 번호로 조회
//    Optional<Question> findByQuestion(int question_id);

    // 랜덤 문제 조회
//    List<Question> randomNext();
//    List<Question> randomPrev();


    // 문제 수정
    List<Question> updateQuestion(int question_id, String variable, String updateContents);

    // 삭제 기능도 추가해야함
    String deleteQuestion(int question_id);

}
