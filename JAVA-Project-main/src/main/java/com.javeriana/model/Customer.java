package com.javeriana.model;

import java.util.UUID;

public class Customer {
    private UUID id;
    private String username;
    private String password;
    private String name;
    private String lastName;
    private int age;

    public Customer (String username, String password, String name, String lastName, int age){
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "id: " + id + " - Username: " + username + " - Password: " + password + " - Name: " + name + " - Lastname: " + lastName + " - Age: " + age;
    }
}
