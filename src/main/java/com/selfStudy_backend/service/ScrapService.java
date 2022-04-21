package com.selfStudy_backend.service;

import com.selfStudy_backend.domain.Scrap;

import java.util.List;

public interface ScrapService {
    //스크랩 저장
    int saveScrap(Scrap scrap);
    //아이디별 스크랩 조회
    List<Scrap> findById(String user_id);
}