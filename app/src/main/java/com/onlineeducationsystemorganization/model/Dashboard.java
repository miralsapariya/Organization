package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {

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
    private List<CoursechartDatum> coursechartData = null;

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

    public List<CoursechartDatum> getCoursechartData() {
        return coursechartData;
    }

    public void setCoursechartData(List<CoursechartDatum> coursechartData) {
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
//
public class CoursechartDatum {

    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("course_name")
    @Expose
    private String course_name;


    @SerializedName("x_chart_label")
    @Expose
    private String xChartLabel;
    @SerializedName("course_yaxis_label")
    @Expose
    private ArrayList<CourseYaxisLabel> courseYaxisLabel = null;
    @SerializedName("course_xaxis_dataval")
    @Expose
    private List<CourseXaxisDataval> courseXaxisDataval = null;


    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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
}