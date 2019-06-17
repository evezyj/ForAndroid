package com.one.frontend.interviewexam.model;

import java.util.List;


public class SubjectInfo {

    private PaperDetail paperDetail;
    private List<PaperSubject> paperSubjects;

    public PaperDetail getPaperDetail() {
        return paperDetail;
    }

    public void setPaperDetail(PaperDetail paperDetail) {
        this.paperDetail = paperDetail;
    }

    public List<PaperSubject> getPaperSubjects() {
        return paperSubjects;
    }

    public void setPaperSubjects(List<PaperSubject> paperSubjects) {
        this.paperSubjects = paperSubjects;
    }

    @Override
    public String toString() {
        return "SubjectInfo{" +
                "paperDetail=" + paperDetail +
                ", paperSubjects=" + paperSubjects +
                '}';
    }
}
