package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.User;

public interface UserRepository {

    //회원저장
    void saveUser(User user);
}
