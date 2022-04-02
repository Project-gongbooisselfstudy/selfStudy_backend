package com.selfstudy.service;

import com.selfstudy.domain.Question;
import com.selfstudy.repository.QuestionRepository;

public class  QuestionService {

    private final QuestionRepository questionRepository;


    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    // 실제 비즈니스 로직


    public Question saveQuestion(Question question) {
        questionRepository.saveQuestion(question);
        System.out.println("========문제 저장 완료========");
        return question;
        }

    // 모든 문제 조회

    public Question findAllQuestion() {
        return null;
    }

}

