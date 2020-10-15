package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Exam {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private List<Datum> data = null;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Datum> getData() {
return data;
}

public void setData(List<Datum> data) {
this.data = data;
}
//
public class Option {

    @SerializedName("option")
    @Expose
    private String option;

    @SerializedName("key")
    @Expose
    private int key;

    @SerializedName("option_matrix")
    @Expose
    private String optionMatrix;

    @SerializedName("path")
    @Expose
    private String path;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    @SerializedName("selected")
    @Expose
    private String selected="";

    @SerializedName("option_order")
    @Expose
    private int option_order=0;

    @SerializedName("blank_value")
    @Expose
    private String blank_value="";

    public String getBlank_value() {
        return blank_value;
    }

    public void setBlank_value(String blank_value) {
        this.blank_value = blank_value;
    }



    public int getOption_order() {
        return option_order;
    }

    public void setOption_order(int option_order) {
        this.option_order = option_order;
    }




    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;


    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getOptionMatrix() {
        return optionMatrix;
    }

    public void setOptionMatrix(String optionMatrix) {
        this.optionMatrix = optionMatrix;
    }

}
//
public class Datum {

    @SerializedName("que_id")
    @Expose
    private Integer queId;
    @SerializedName("que_type")
    @Expose
    private Integer queType;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("course_name")
    @Expose
    private String courseName;

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    @SerializedName("paragraph")
    @Expose
    private String paragraph;



    public String getQuiz_over() {
        return quiz_over;
    }

    public void setQuiz_over(String quiz_over) {
        this.quiz_over = quiz_over;
    }

    @SerializedName("quiz_over")
    @Expose
    private String quiz_over="";


    public int getSub_type() {
        return sub_type;
    }

    public void setSub_type(int sub_type) {
        this.sub_type = sub_type;
    }

    @SerializedName("sub_type")
    @Expose
    private int sub_type;

    @SerializedName("options")
    @Expose
    private ArrayList<Option> options = null;

    public Integer getQueId() {
        return queId;
    }

    public void setQueId(Integer queId) {
        this.queId = queId;
    }

    public Integer getQueType() {
        return queType;
    }

    public void setQueType(Integer queType) {
        this.queType = queType;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

}

}