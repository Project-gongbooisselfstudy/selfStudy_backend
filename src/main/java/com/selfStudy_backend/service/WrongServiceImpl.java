package com.selfStudy_backend.service;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Wrong;
import com.selfStudy_backend.repository.JDBCWrongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WrongServiceImpl {

    private final JDBCWrongRepository jdbcWrongRepository;

    public WrongServiceImpl(JDBCWrongRepository jdbcWrongRepository) {
        this.jdbcWrongRepository = jdbcWrongRepository;
    }

    public List<Question> findAllQuestion(String user_id) {
        return jdbcWrongRepository.findAll(user_id);
    }


    public String solve(List<Question> questionList,String inputAnswer) {
        String genuineAnswer = questionList.get(0).getAnswer();
        System.out.println("genuineAnswer = " + genuineAnswer);
        System.out.println("inputAnswer = " + inputAnswer);
        int question_id = questionList.get(0).getQuestion_id();
        boolean validate = validateAnswer(genuineAnswer,inputAnswer);
//        String user_id = questionList.get(0).getUser_id();

        if (validate == true) {
            jdbcWrongRepository.updateWrong(question_id);
            return "정답입니다";
        }
        return "오답입니다";

    }

    private boolean validateAnswer(String genuineAnswer,String inputAnswer) {
        if (genuineAnswer.equals(inputAnswer)) {return true;}
        else {return false;} }

}




