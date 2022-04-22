package com.selfStudy_backend.service;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Scrap;
import com.selfStudy_backend.repository.JDBCScrapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapServiceImple implements ScrapService{

    private final JDBCScrapRepository jdbcScrapRepository;

    public ScrapServiceImple(JDBCScrapRepository jdbcScrapRepository){
        this.jdbcScrapRepository = jdbcScrapRepository;
    }

    @Override
    public String saveScrap(Scrap scrap) {
        if (jdbcScrapRepository.findByUserIdAndQuestionId(scrap.getUser_id(), scrap.getQuestion_id()).isPresent()){
            return "이미 스크랩되었습니다";
        } else {
            jdbcScrapRepository.saveScrap(scrap);
        }
        return scrap.getUser_id();
    }

    @Override
    public List<Question> findById(String user_id) {
        return jdbcScrapRepository.findById(user_id);
    }

    @Override
    public String deleteScrap(String user_id, int question_id){
        return jdbcScrapRepository.deleteScrap(user_id, question_id);
    }
}
