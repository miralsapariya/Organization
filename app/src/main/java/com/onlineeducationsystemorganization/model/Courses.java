package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Courses {

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
public static class Courseslist {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("owner_name")
    @Expose
    private String ownerName;
    @SerializedName("course_image")
    @Expose
    private String courseImage;
    @SerializedName("total_user")
    @Expose
    private Integer totalUser;
    @SerializedName("total_assigned_user")
    @Expose
    private Integer totalAssignedUser;
    @SerializedName("remaining_user")
    @Expose
    private Integer remainingUser;

    public Integer getIs_assigned() {
        return is_assigned;
    }

    public void setIs_assigned(Integer is_assigned) {
        this.is_assigned = is_assigned;
    }

    @SerializedName("is_assigned")
    @Expose
    private Integer is_assigned;

    @SerializedName("is_started")
    @Expose
    private Integer is_started;

    public Integer getIs_started() {
        return is_started;
    }

    public void setIs_started(Integer is_started) {
        this.is_started = is_started;
    }

    private boolean isSelected;
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public Integer getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(Integer totalUser) {
        this.totalUser = totalUser;
    }

    public Integer getTotalAssignedUser() {
        return totalAssignedUser;
    }

    public void setTotalAssignedUser(Integer totalAssignedUser) {
        this.totalAssignedUser = totalAssignedUser;
    }

    public Integer getRemainingUser() {
        return remainingUser;
    }

    public void setRemainingUser(Integer remainingUser) {
        this.remainingUser = remainingUser;
    }

}
//
public class Datum {

    @SerializedName("courseslist")
    @Expose
    private ArrayList<Courseslist> courseslist = null;

    public ArrayList<Courseslist> getCourseslist() {
        return courseslist;
    }

    public void setCourseslist(ArrayList<Courseslist> courseslist) {
        this.courseslist = courseslist;
    }

}
}
