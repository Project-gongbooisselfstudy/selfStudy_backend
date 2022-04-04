package com.selfstudy.service;

import com.selfstudy.domain.Question;
import com.selfstudy.repository.JDBCQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    
//    private final QuestionRepository questionRepository ;
//
//    public QuestionServiceImpl(QuestionRepository questionRepository) {
//        this.questionRepository = JDBCQuestionRepository;
//    }

    private final JDBCQuestionRepository jdbcQuestionRepository;

    public QuestionServiceImpl(JDBCQuestionRepository jdbcQuestionRepository) {
        this.jdbcQuestionRepository = jdbcQuestionRepository;
    }

    @Override
    public int saveQuestion(Question question) {
//        validateDuplicateQuestion(question);
        jdbcQuestionRepository.saveQuestion(question);
        System.out.println("===========Controller: DB에 저장 완료===========");
        return question.getId(); 
    }
    
    @Override
    public List<Question> findAllQuestion() {
        return jdbcQuestionRepository.findAll();
    }

    @Override
    public Question modifyQuestion(Question question) {
        return null;
    }

    @Override
    public void deleteQuestion(Question question) {

    }

    private void validateDuplicateQuestion(Question question) {
        try {
            List<Question> tmp = jdbcQuestionRepository.findByQuestion(question.getContents());

            if (tmp.size() != 0) {
                throw new IllegalStateException("이미 존재하는 문제입니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}

