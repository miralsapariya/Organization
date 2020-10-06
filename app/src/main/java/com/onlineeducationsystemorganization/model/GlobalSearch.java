package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GlobalSearch implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    private final static long serialVersionUID = -3030539379106708532L;

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

    public class Datum implements Serializable
    {


        @SerializedName("courseslist")
        @Expose
        private ArrayList<Courseslist> courseslist = null;
        private final static long serialVersionUID = -6237049077332473671L;

        public ArrayList<Courseslist> getCourseslist() {
            return courseslist;
        }

        public void setCourseslist(ArrayList<Courseslist> courseslist) {
            this.courseslist = courseslist;
        }


    }
    //

    public class Courseslist implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("course_name")
        @Expose
        private String courseName;
        @SerializedName("course_price")
        @Expose
        private String coursePrice;
        @SerializedName("course_old_price")
        @Expose
        private String courseOldPrice;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("instructor_name")
        @Expose
        private String instructorName;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("publish_on")
        @Expose
        private String publishOn;

        @SerializedName("is_added")
        @Expose
        private Integer is_added;

        private final static long serialVersionUID = -5592562109560271613L;


        public Integer getIs_added() {
            return is_added;
        }

        public void setIs_added(Integer is_added) {
            this.is_added = is_added;
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

        public String getCoursePrice() {
            return coursePrice;
        }

        public void setCoursePrice(String coursePrice) {
            this.coursePrice = coursePrice;
        }

        public String getCourseOldPrice() {
            return courseOldPrice;
        }

        public void setCourseOldPrice(String courseOldPrice) {
            this.courseOldPrice = courseOldPrice;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getInstructorName() {
            return instructorName;
        }

        public void setInstructorName(String instructorName) {
            this.instructorName = instructorName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getPublishOn() {
            return publishOn;
        }

        public void setPublishOn(String publishOn) {
            this.publishOn = publishOn;
        }

    }
}