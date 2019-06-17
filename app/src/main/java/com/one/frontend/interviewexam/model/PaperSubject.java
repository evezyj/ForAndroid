package com.one.frontend.interviewexam.model;

import java.util.List;

public class PaperSubject {
    private int id;
    private int pdId;
    private int subjectId;
    private String subjectName;
    private List<SubjectOption> subjectOptions;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPdId() {
        return pdId;
    }

    public void setPdId(int pdId) {
        this.pdId = pdId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<SubjectOption> getSubjectOptions() {
        return subjectOptions;
    }

    public void setSubjectOptions(List<SubjectOption> subjectOptions) {
        this.subjectOptions = subjectOptions;
    }

    @Override
    public String toString() {
        return "PaperSubject{" +
                "id=" + id +
                ", pdId=" + pdId +
                ", subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", subjectOptions=" + subjectOptions +
                '}';
    }
}
