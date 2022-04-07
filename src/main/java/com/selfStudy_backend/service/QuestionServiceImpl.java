package com.selfStudy_backend.service;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.repository.JDBCQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final JDBCQuestionRepository jdbcQuestionRepository;

    public QuestionServiceImpl(JDBCQuestionRepository jdbcQuestionRepository) {
        this.jdbcQuestionRepository = jdbcQuestionRepository;
    }


    @Override
    public int saveQuestion(Question question) {
//        validateDuplicateQuestion(question);
        jdbcQuestionRepository.saveQuestion(question);
        return question.getQuestion_id();
    }
    
    @Override
    public List<Question> findAllQuestion() {
        return jdbcQuestionRepository.findAll();
    }

    @Override
    public List<Question> findById(String user_id){
        return jdbcQuestionRepository.findById(user_id);

    }

    @Override
    public Optional<Question> findByQuestion(int question_id){
        return jdbcQuestionRepository.findByQuestion(question_id);
    }


    @Override
    public Question modifyQuestion(Question question) {
        return null;
    }

    @Override
    public String deleteQuestion(int question_id) {
        return jdbcQuestionRepository.deleteQuestion(question_id);
    }



//    private void validateDuplicateQuestion(Question question) {
//        try {
//            List<Question> tmp = jdbcQuestionRepository.findByQuestion(question.getContents());
//
//            if (tmp.size() != 0) {
//                throw new IllegalStateException("이미 존재하는 문제입니다");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
    }



