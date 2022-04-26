package com.selfStudy_backend.domain;
import lombok.Data;

@Data
public class UpdateQuestion {
    private int question_id ;
    private String category ;
    private String updateContents ;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUpdateContents() {
        return updateContents;
    }

    public void setUpdateContents(String updateContents) {
        this.updateContents = updateContents;
    }

}


