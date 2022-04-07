package com.selfStudy_backend.service;

import com.selfStudy_backend.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {


    int saveQuestion(Question question);

    List<Question> findAllQuestion();

    List<Question> findById(String user_id);

    Optional<Question> findByQuestion(int question_id);

    Question modifyQuestion(Question question);

    String deleteQuestion(int question_id);



}
