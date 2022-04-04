package com.selfstudy.service;

import com.selfstudy.domain.Question;

import java.util.List;

public interface QuestionService {


    int saveQuestion(Question question);

    List<Question> findAllQuestion();

    Question modifyQuestion(Question question);

    void deleteQuestion(Question question);



}
