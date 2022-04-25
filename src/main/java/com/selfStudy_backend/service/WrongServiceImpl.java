package com.selfStudy_backend.service;

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

    public List<Wrong> findAllQuestion(String user_id) {
        System.out.println("service 실행");
        return jdbcWrongRepository.findAll(user_id);
    }
}
