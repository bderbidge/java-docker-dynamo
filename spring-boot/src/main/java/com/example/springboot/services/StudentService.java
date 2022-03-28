package com.example.springboot.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.springboot.dtos.Student;
import com.example.springboot.dtos.StudentDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private DynamoDBMapper mapper;

    public StudentDTO insertIntoDynamoDB(StudentDTO dto) {
        Student student = new Student();
        student.setid(dto.getid());
        student.setfullName(dto.getfullName());
        student.setphoneNumber(dto.getphoneNumber());
        mapper.save(student);
        return convertDto(student);
    }

    public StudentDTO convertDto(Student student) {
        return new StudentDTO(
                student.getid().toString(),
                student.getfullName(),
                student.getphoneNumber());
    }
}