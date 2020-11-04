package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CompletedCourses {

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
public class CoursechartDropdownlist {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_color_code")
    @Expose
    private String courseColorCode;

    private boolean isSelected =true;

    public boolean isSelected() {
        return this.isSelected;
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

    public String getCourseColorCode() {
        return courseColorCode;
    }

    public void setCourseColorCode(String courseColorCode) {
        this.courseColorCode = courseColorCode;
    }

}
//
public class Datum {

    @SerializedName("total_users")
    @Expose
    private Integer totalUsers;
    @SerializedName("total_courses")
    @Expose
    private Integer totalCourses;
    @SerializedName("total_completed_course")
    @Expose
    private Integer totalCompletedCourse;
    @SerializedName("total_active_course")
    @Expose
    private Integer totalActiveCourse;
    @SerializedName("mycourselist")
    @Expose
    private List<Mycourselist> mycourselist = null;
    @SerializedName("most_completed_courses")
    @Expose
    private ArrayList<MostCompletedCourse> mostCompletedCourses = null;
    @SerializedName("coursechart_dropdownlist")
    @Expose
    private ArrayList<CoursechartDropdownlist> coursechartDropdownlist = null;
    @SerializedName("coursechart_data")
    @Expose
    private List<Object> coursechartData = null;

    public Integer getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Integer totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Integer getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Integer totalCourses) {
        this.totalCourses = totalCourses;
    }

    public Integer getTotalCompletedCourse() {
        return totalCompletedCourse;
    }

    public void setTotalCompletedCourse(Integer totalCompletedCourse) {
        this.totalCompletedCourse = totalCompletedCourse;
    }

    public Integer getTotalActiveCourse() {
        return totalActiveCourse;
    }

    public void setTotalActiveCourse(Integer totalActiveCourse) {
        this.totalActiveCourse = totalActiveCourse;
    }

    public List<Mycourselist> getMycourselist() {
        return mycourselist;
    }

    public void setMycourselist(List<Mycourselist> mycourselist) {
        this.mycourselist = mycourselist;
    }

    public ArrayList<MostCompletedCourse> getMostCompletedCourses() {
        return mostCompletedCourses;
    }

    public void setMostCompletedCourses(ArrayList<MostCompletedCourse> mostCompletedCourses) {
        this.mostCompletedCourses = mostCompletedCourses;
    }

    public ArrayList<CoursechartDropdownlist> getCoursechartDropdownlist() {
        return coursechartDropdownlist;
    }

    public void setCoursechartDropdownlist(ArrayList<CoursechartDropdownlist> coursechartDropdownlist) {
        this.coursechartDropdownlist = coursechartDropdownlist;
    }

    public List<Object> getCoursechartData() {
        return coursechartData;
    }

    public void setCoursechartData(List<Object> coursechartData) {
        this.coursechartData = coursechartData;
    }

}
//
public class MostCompletedCourse {

    @SerializedName("percent_of_course")
    @Expose
    private String percentOfCourse;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("count_of_course")
    @Expose
    private String countOfCourse;




    public String getPercentOfCourse() {
        return percentOfCourse;
    }

    public void setPercentOfCourse(String percentOfCourse) {
        this.percentOfCourse = percentOfCourse;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCountOfCourse() {
        return countOfCourse;
    }

    public void setCountOfCourse(String countOfCourse) {
        this.countOfCourse = countOfCourse;
    }

}
//
public class Mycourselist {

    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_id")
    @Expose
    private Integer courseId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subscription_date")
    @Expose
    private String subscriptionDate;
    @SerializedName("course_status")
    @Expose
    private String courseStatus;
    @SerializedName("total_user")
    @Expose
    private Integer totalUser;
    @SerializedName("total_assigned_user")
    @Expose
    private Integer totalAssignedUser;
    @SerializedName("remaining_user")
    @Expose
    private Integer remainingUser;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
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

}