package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
    @JsonProperty
    private int id;
    @JsonProperty
    private String fio;

    public Student() {}

    // Конструктор
    public Student(int id, String fio) {
        this.id = id;
        this.fio = fio;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    @Override
    public String toString() {
        return "{ \"id\": " + this.id + ", \"fio\":\"" + this.fio + "\" }";
    }
}
