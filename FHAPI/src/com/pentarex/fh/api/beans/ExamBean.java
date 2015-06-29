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
public class ExamBean {

    private String term;
    private String title;
    private String type;
    private String mode;
    private String status;
    private double date;
    private double registrationEnd;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDate() {
        return date;
    }

    public void setDate(double date) {
        this.date = date;
    }

    public double getRegistrationEnd() {
        return registrationEnd;
    }

    public void setRegistrationEnd(double registrationEnd) {
        this.registrationEnd = registrationEnd;
    }
}
