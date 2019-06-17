package com.one.frontend.interviewexam.model;

public class UserInfo {
    private int id;
    private String name;
    private String email;
    private int age;
    private int countCorrect;
    private String trueSubject;
    private String falseSubject;
    private int pdId;

    public int getPdId() {
        return pdId;
    }

    public void setPdId(int pdId) {
        this.pdId = pdId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCountCorrect() {
        return countCorrect;
    }

    public void setCountCorrect(int countCorrect) {
        this.countCorrect = countCorrect;
    }

    public String getTrueSubject() {
        return trueSubject;
    }

    public void setTrueSubject(String trueSubject) {
        this.trueSubject = trueSubject;
    }

    public String getFalseSubject() {
        return falseSubject;
    }

    public void setFalseSubject(String falseSubject) {
        this.falseSubject = falseSubject;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", countCorrect=" + countCorrect +
                ", trueSubject='" + trueSubject + '\'' +
                ", falseSubject='" + falseSubject + '\'' +
                ", pdId=" + pdId +
                '}';
    }
}
