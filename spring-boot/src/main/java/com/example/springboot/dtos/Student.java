package com.example.springboot.dtos;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "student")
public class Student {
    private String id;
    private String fullName;
    private String phoneNumber;

    public Student() {
    }

    public Student(String id, String fullName, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    @DynamoDBHashKey(attributeName = "id")
    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "fullName")
    public String getfullName() {
        return fullName;
    }

    public void setfullName(String fullName) {
        this.fullName = fullName;
    }

    @DynamoDBHashKey(attributeName = "phone_number")
    public String getphoneNumber() {
        return phoneNumber;
    }

    public void setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}