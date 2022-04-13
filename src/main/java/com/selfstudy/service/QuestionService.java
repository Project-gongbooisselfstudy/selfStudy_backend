package com.selfstudy.service;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.selfstudy.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {


    int saveQuestion(Question question);

    List<Question> findAllQuestion();

    List<Question> findById(String user_id);

    Optional<Question> findByQuestion(int question_id);

    List<Question> randomQuestionNext();

    List<Question> randomQuestionPrev();

    Question modifyQuestion(Question question);

    String deleteQuestion(int question_id);



}
