package com.selfStudy_backend.service;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.repository.JDBCQuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    private final JDBCQuestionRepository jdbcQuestionRepository;

    public QuestionServiceImpl(JDBCQuestionRepository jdbcQuestionRepository) {
        this.jdbcQuestionRepository = jdbcQuestionRepository;
    }


    @Override
    // TODO 문제를 중복 입력에 대한 검증과정이 없는 상태 : service 부분에 추가해야함
    public int saveQuestion(Question question) {
        jdbcQuestionRepository.saveQuestion(question);
        return question.getQuestion_id();
    }

    @Override
    public List<Question> findAllQuestion(String user_id) {
        return jdbcQuestionRepository.findById(user_id);
    }

    @Override
    public List<Question> updateQuestion(int question_id, String variable, String updateContents) {
        return jdbcQuestionRepository.updateQuestion(question_id, variable, updateContents);
    }

    @Override
    public String deleteQuestion(int question_id) {
        return jdbcQuestionRepository.deleteQuestion(question_id);
    }

    public String solve(List<Question> questionList,String inputAnswer) {
        String genuineAnswer = questionList.get(0).getAnswer();
        log.info("genuineAnswer = " + genuineAnswer);
        log.info("inputAnswer = " + inputAnswer);
        int question_id = questionList.get(0).getQuestion_id();
        boolean validate = validateAnswer(genuineAnswer,inputAnswer);
        String user_id = questionList.get(0).getUser_id();
        if (validate == false) {
            jdbcQuestionRepository.updateWrong(question_id,user_id);
            return "오답입니다";
        }
        return "정답입니다";

    }


    private boolean validateAnswer(String genuineAnswer,String inputAnswer) {
        if (genuineAnswer.equals(inputAnswer)) {return true;}
        else {return false;} }}





