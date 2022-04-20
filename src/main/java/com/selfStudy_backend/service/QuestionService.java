package com.selfStudy_backend.service;

import com.selfStudy_backend.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {


    int saveQuestion(Question question);

    List<Question> findAllQuestion(String user_id);

//    List<Question> findById(String user_id);

//    Optional<Question> findByQuestion(int question_id);

//    List<Question> randomQuestionNext();

//    List<Question> randomQuestionPrev();

    List<Question> updateQuestion(int question_id , String variable, String updateContents);

    String deleteQuestion(int question_id);



}
