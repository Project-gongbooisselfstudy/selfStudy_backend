package com.selfstudy.controller;

import com.selfstudy.domain.Question;
import com.selfstudy.repository.MemoryQuestionRepository;
import com.selfstudy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuestionController {
    private final QuestionService questionService;


    @PostMapping("/hello")
    public void save(@RequestBody Question question){
        return question.();
    }



}