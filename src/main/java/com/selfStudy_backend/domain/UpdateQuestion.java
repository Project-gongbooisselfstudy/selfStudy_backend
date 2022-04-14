package com.selfStudy_backend.domain;
import lombok.Data;

@Data
public class UpdateQuestion {
    private int question_id ;
    private String variable ;
    private String updateContents ;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getUpdateContents() {
        return updateContents;
    }

    public void setUpdateContents(String updateContents) {
        this.updateContents = updateContents;
    }

}


