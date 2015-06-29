/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pentarex.fh.api.beans;

/**
 *
 * @author labnb057
 */
public class MarksBean {
    private String term;
    private String title;
    private String gradeWords;
    private int grade;
    private double titleId;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGradeWords() {
        return gradeWords;
    }

    public void setGradeWords(String gradeWords) {
        this.gradeWords = gradeWords;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public double getTitleId() {
        return titleId;
    }

    public void setTitleId(double titleId) {
        this.titleId = titleId;
    }
    
    
}
