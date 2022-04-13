package com.selfStudy_backend.repository;

import com.selfStudy_backend.domain.User;

import java.util.Optional;

public interface UserRepository {

    //회원저장
    void saveUser(User user);
    Optional<User> findById(String id);
}
