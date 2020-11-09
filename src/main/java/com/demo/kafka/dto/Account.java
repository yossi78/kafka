package com.demo.kafka.dto;


import lombok.Data;

import java.io.Serializable;


@Data
public class Account implements Serializable {

    private Long id;
    private String name;
    private int age;

    public Account() {
    }

    public Account(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Account(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setAll(Long id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }



}

