package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserDashBoard {

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

    @SerializedName("total_users")
    @Expose
    private Integer totalUsers;
    @SerializedName("total_courses")
    @Expose
    private Integer totalCourses;
    @SerializedName("total_completed_course")
    @Expose
    private Integer totalCompletedCourse;
    @SerializedName("chart_data")
    @Expose
    private ChartData chartData;
    @SerializedName("chart_data_per")
    @Expose
    private ChartDataPer chartDataPer;
    @SerializedName("mycourselist")
    @Expose
    private ArrayList<Mycourselist> mycourselist = null;

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

    public ChartData getChartData() {
        return chartData;
    }

    public void setChartData(ChartData chartData) {
        this.chartData = chartData;
    }

    public ChartDataPer getChartDataPer() {
        return chartDataPer;
    }

    public void setChartDataPer(ChartDataPer chartDataPer) {
        this.chartDataPer = chartDataPer;
    }

    public ArrayList<Mycourselist> getMycourselist() {
        return mycourselist;
    }

    public void setMycourselist(ArrayList<Mycourselist> mycourselist) {
        this.mycourselist = mycourselist;
    }

}
//
public class ChartData {

    @SerializedName("not_started")
    @Expose
    private Integer notStarted;
    @SerializedName("in_progress")
    @Expose
    private Integer inProgress;
    @SerializedName("completed")
    @Expose
    private Integer completed;

    public Integer getNotStarted() {
        return notStarted;
    }

    public void setNotStarted(Integer notStarted) {
        this.notStarted = notStarted;
    }

    public Integer getInProgress() {
        return inProgress;
    }

    public void setInProgress(Integer inProgress) {
        this.inProgress = inProgress;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
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
    @SerializedName("download_link")
    @Expose
    private String downloadLink;
    @SerializedName("button")
    @Expose
    private String button;
    @SerializedName("section_id")
    @Expose
    private String sectionId;
    @SerializedName("slide_id")
    @Expose
    private String slideId;
    @SerializedName("course_status")
    @Expose
    private String courseStatus;

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

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSlideId() {
        return slideId;
    }

    public void setSlideId(String slideId) {
        this.slideId = slideId;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

}
//
public class ChartDataPer {

    @SerializedName("not_started_per")
    @Expose
    private Float notStartedPer;
    @SerializedName("in_progress_per")
    @Expose
    private Float inProgressPer;
    @SerializedName("completed_per")
    @Expose
    private Float completedPer;

    public Float getNotStartedPer() {
        return notStartedPer;
    }

    public void setNotStartedPer(Float notStartedPer) {
        this.notStartedPer = notStartedPer;
    }

    public Float getInProgressPer() {
        return inProgressPer;
    }

    public void setInProgressPer(Float inProgressPer) {
        this.inProgressPer = inProgressPer;
    }

    public Float getCompletedPer() {
        return completedPer;
    }

    public void setCompletedPer(Float completedPer) {
        this.completedPer = completedPer;
    }

}
//

}