package com.example.recyclerviewcrud.model;

import java.io.Serializable;

public class Person implements Serializable {
    private int id; // auto generated by db
    private String surname;
    private String name;

    public Person() {
    }

    public Person(int id, String surname, String name) {
        this(surname, name);
        this.id = id;
    }

    public Person(String surname, String name) {
        this.surname = surname;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
