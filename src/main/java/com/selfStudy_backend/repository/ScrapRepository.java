package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.Scrap;

import java.util.List;

public interface ScrapRepository {

    //스크랩 저장
    void saveScrap(Scrap scrap);

    //스크랩 아이디로 조회
    List<Scrap> findById(String user_id);

    //스크랩 삭제

}
