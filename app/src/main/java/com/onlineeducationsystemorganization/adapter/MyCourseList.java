package com.onlineeducationsystemorganization.adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MyCourseList {

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
public class Datum {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("courseslist")
    @Expose
    private ArrayList<Courseslist> courseslist = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Courseslist> getCourseslist() {
        return courseslist;
    }

    public void setCourseslist(ArrayList<Courseslist> courseslist) {
        this.courseslist = courseslist;
    }

}
//
public class Courseslist {

    @SerializedName("course_id")
    @Expose
    private Integer courseId;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("progress")
    @Expose
    private String progress;
    @SerializedName("button_label")
    @Expose
    private String buttonLabel;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("course_status")
    @Expose
    private String course_status;
    @SerializedName("next_section_id")
    @Expose
    private String next_section_id;
    @SerializedName("next_slide_id")
    @Expose
    private String next_slide_id;

    public Integer getIs_coursereset() {
        return is_coursereset;
    }

    public void setIs_coursereset(Integer is_coursereset) {
        this.is_coursereset = is_coursereset;
    }

    @SerializedName("is_coursereset")
    @Expose
    private  Integer is_coursereset;


    public String getCertificate_link() {
        return certificate_link;
    }

    public void setCertificate_link(String certificate_link) {
        this.certificate_link = certificate_link;
    }

    @SerializedName("certificate_link")
    @Expose
    private String certificate_link;


    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    @SerializedName("percentage")
    @Expose
    private Integer percentage;

    public String getCourse_status() {
        return course_status;
    }

    public void setCourse_status(String course_status) {
        this.course_status = course_status;
    }

    public String getNext_section_id() {
        return next_section_id;
    }

    public void setNext_section_id(String next_section_id) {
        this.next_section_id = next_section_id;
    }

    public String getNext_slide_id() {
        return next_slide_id;
    }

    public void setNext_slide_id(String next_slide_id) {
        this.next_slide_id = next_slide_id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getButtonLabel() {
        return buttonLabel;
    }

    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
}