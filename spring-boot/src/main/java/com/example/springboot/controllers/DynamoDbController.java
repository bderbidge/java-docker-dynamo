package com.example.springboot.controllers;

import com.example.springboot.dtos.StudentDTO;
import com.example.springboot.services.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dynamoDb")
public class DynamoDbController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public StudentDTO insertIntoDynamoDB(@RequestBody StudentDTO dto) {
        return studentService.insertIntoDynamoDB(dto);
    }
}