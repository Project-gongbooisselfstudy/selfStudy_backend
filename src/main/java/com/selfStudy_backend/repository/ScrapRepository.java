package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Scrap;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository {

    //스크랩 저장
    void saveScrap(Scrap scrap);

    //스크랩 아이디로 조회
    List<Question> findById(String user_id);

    //
    Optional<Scrap> findByUserIdAndQuestionId(String user_id, int question_id);

    //스크랩 삭제
    String deleteScrap(String user_id, int question_id);
}
