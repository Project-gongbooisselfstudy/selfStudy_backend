package com.selfstudy.repository;

import com.selfstudy.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class MemoryQuestionRepository implements QuestionRepository {

    private List store = new ArrayList<>();
    private final QuestionRepository questionRepository = new MemoryQuestionRepository();

    @Override
    public Question findById(int questionId) {
        return questionRepository.findById(questionId);
    }

    @Override
    public void modifyQuestion(Question question) {
    }
}
