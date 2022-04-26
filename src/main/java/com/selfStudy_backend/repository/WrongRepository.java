package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.Question;
import com.selfStudy_backend.domain.Wrong;

import java.util.List;

public interface WrongRepository {

    //오답문제 조회 id 별로
    List<Question> findAll(String user_id);

    //오답문제 로딩
    List<Question> loadWrong();

//    오답문제 풀기
//    List<Wrong> updateWrong();
//

}
