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
    public List<Question> updateQuestion(int question_id, String variable, String updateContents) {
        return jdbcQuestionRepository.updateQuestion(question_id, variable, updateContents);
    }

    @Override
    public String deleteQuestion(int question_id) {
        return jdbcQuestionRepository.deleteQuestion(question_id);
    }

    @Override
    public List<Question> randomQuestionNext() { return jdbcQuestionRepository.randomNext();}

    public List<Question> solve(String inputAnswer) {
        List<Question> questionSet = randomQuestionNext();
        String genuineAnswer = questionSet.get(0).getAnswer();
        int question_id = questionSet.get(0).getQuestion_id();
        System.out.println(genuineAnswer);
        System.out.println(question_id);
        boolean validate = validateAnswer(genuineAnswer,inputAnswer);
        if (validate == false) {
            System.out.println("오답입니다");
            return jdbcQuestionRepository.updateWrong(question_id);
        }
        System.out.println("정답입니다");
        return questionSet;
    }

    @Override
    public List<Question> randomQuestionPrev() {
        return jdbcQuestionRepository.randomPrev();
    }



    private boolean validateAnswer(String genuineAnswer,String inputAnswer) {
        if (genuineAnswer.equals(inputAnswer)) {
            return true;
        }
        else {
            return false;
        }
    }




//    private void validateDuplicateQuestion(Question question) {
//        try {
//            List<Question> tmp = jdbcQuestionRepository.findByQuestion(question.getContents());

//        } catch (Exception e) {//
////            if (tmp.size() != 0) {
////                throw new IllegalStateException("이미 존재하는 문제입니다");
////            }
//            e.printStackTrace();
//        }

}



