package com.example.springboot.dtos;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "movie")
public class Movie {
    private int year;
    private String title;
    private String phoneNumber;

    public Movie() {
    }

    public Movie(int year, String title, String phoneNumber) {
        this.year = year;
        this.title = title;
        this.phoneNumber = phoneNumber;
    }

    @DynamoDBHashKey(attributeName = "year")
    public int getyear() {
        return year;
    }

    public void setyear(int year) {
        this.year = year;
    }

    @DynamoDBAttribute
    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    @DynamoDBHashKey(attributeName = "phone_number")
    public String getphoneNumber() {
        return phoneNumber;
    }

    public void setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}