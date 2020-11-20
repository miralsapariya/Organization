package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DashboardReport {

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
    @SerializedName("not_started_course")
    @Expose
    private Integer notStartedCourse;
    @SerializedName("total_completed_course")
    @Expose
    private Integer totalCompletedCourse;
    @SerializedName("total_in_progress_course")
    @Expose
    private Integer totalInProgressCourse;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_color_code")
    @Expose
    private String courseColorCode;
    @SerializedName("useractivitylist")
    @Expose
    private ArrayList<Useractivitylist> useractivitylist = null;
    @SerializedName("coursechart_data")
    @Expose
    private List<CoursechartDatum> coursechartData = null;

    public Integer getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Integer totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Integer getNotStartedCourse() {
        return notStartedCourse;
    }

    public void setNotStartedCourse(Integer notStartedCourse) {
        this.notStartedCourse = notStartedCourse;
    }

    public Integer getTotalCompletedCourse() {
        return totalCompletedCourse;
    }

    public void setTotalCompletedCourse(Integer totalCompletedCourse) {
        this.totalCompletedCourse = totalCompletedCourse;
    }

    public Integer getTotalInProgressCourse() {
        return totalInProgressCourse;
    }

    public void setTotalInProgressCourse(Integer totalInProgressCourse) {
        this.totalInProgressCourse = totalInProgressCourse;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    public ArrayList<Useractivitylist> getUseractivitylist() {
        return useractivitylist;
    }

    public void setUseractivitylist(ArrayList<Useractivitylist> useractivitylist) {
        this.useractivitylist = useractivitylist;
    }

    public List<CoursechartDatum> getCoursechartData() {
        return coursechartData;
    }

    public void setCoursechartData(List<CoursechartDatum> coursechartData) {
        this.coursechartData = coursechartData;
    }

}
//
public class Useractivitylist {

    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("slide")
    @Expose
    private String slide;
    @SerializedName("attempts")
    @Expose
    private String attempts;
    @SerializedName("completed_date")
    @Expose
    private String completedDate;
    @SerializedName("download_link")
    @Expose
    private String downloadLink;
    @SerializedName("button")
    @Expose
    private String button;

    @SerializedName("btnStatus")
    @Expose
    private String btnStatus;

    public String getBtnStatus() {
        return btnStatus;
    }

    public void setBtnStatus(String btnStatus) {
        this.btnStatus = btnStatus;
    }

    @SerializedName("course_status")
    @Expose
    private String courseStatus;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSlide() {
        return slide;
    }

    public void setSlide(String slide) {
        this.slide = slide;
    }

    public String getAttempts() {
        return attempts;
    }

    public void setAttempts(String attempts) {
        this.attempts = attempts;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
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

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }
}
    //
    public class CourseYaxisLabel {

        @SerializedName("ydata")
        @Expose
        private String ydata;

        public String getYdata() {
            return ydata;
        }

        public void setYdata(String ydata) {
            this.ydata = ydata;
        }

    }
    //
    public class CourseXaxisDataval {

        @SerializedName("xdata")
        @Expose
        private Integer xdata;

        public Integer getXdata() {
            return xdata;
        }

        public void setXdata(Integer xdata) {
            this.xdata = xdata;
        }

    }
    //
    public class CoursechartDatum {

        @SerializedName("course_id")
        @Expose
        private String courseId;
        @SerializedName("course_name")
        @Expose
        private String courseName;
        @SerializedName("x_chart_label")
        @Expose
        private String xChartLabel;
        @SerializedName("course_yaxis_label")
        @Expose
        private ArrayList<CourseYaxisLabel> courseYaxisLabel = null;
        @SerializedName("course_xaxis_dataval")
        @Expose
        private List<CourseXaxisDataval> courseXaxisDataval = null;

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getXChartLabel() {
            return xChartLabel;
        }

        public void setXChartLabel(String xChartLabel) {
            this.xChartLabel = xChartLabel;
        }

        public ArrayList<CourseYaxisLabel> getCourseYaxisLabel() {
            return courseYaxisLabel;
        }

        public void setCourseYaxisLabel(ArrayList<CourseYaxisLabel> courseYaxisLabel) {
            this.courseYaxisLabel = courseYaxisLabel;
        }

        public List<CourseXaxisDataval> getCourseXaxisDataval() {
            return courseXaxisDataval;
        }

        public void setCourseXaxisDataval(List<CourseXaxisDataval> courseXaxisDataval) {
            this.courseXaxisDataval = courseXaxisDataval;
        }

    }
}