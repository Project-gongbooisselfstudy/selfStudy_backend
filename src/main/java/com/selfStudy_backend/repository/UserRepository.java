package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.User;

import java.util.Optional;

public interface UserRepository {

    //회원저장
    void saveUser(User user);
    //아이디로 회원 조회
    Optional<User> findById(String id);
}
