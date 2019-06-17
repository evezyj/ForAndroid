package com.one.frontend.interviewexam.model;

public class AnswerChecked {
    private int id;
    private String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "AnswerChecked{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                '}';
    }
}
