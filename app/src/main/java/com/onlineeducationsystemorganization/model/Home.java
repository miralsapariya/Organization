package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Home implements Serializable {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("meesage")
    @Expose
    private String meesage;
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;
    private final static long serialVersionUID = -7279233530674144744L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMeesage() {
        return meesage;
    }

    public void setMeesage(String meesage) {
        this.meesage = meesage;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    //
    public class Datum implements Serializable
    {

        @SerializedName("banners_list")
        @Expose
        private ArrayList<BannersList> bannersList = null;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("list")
        @Expose
        private ArrayList<List1> list = null;
        private final static long serialVersionUID = 8074214155791479344L;

        public ArrayList<BannersList> getBannersList() {
            return bannersList;
        }

        public void setBannersList(ArrayList<BannersList> bannersList) {
            this.bannersList = bannersList;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ArrayList<List1> getList() {
            return list;
        }

        public void setList(ArrayList<List1> list) {
            this.list = list;
        }


    }
    //
    public class BannersList implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("banner_title")
        @Expose
        private String bannerTitle;
        @SerializedName("banner_description")
        @Expose
        private String bannerDescription;
        @SerializedName("banner_image")
        @Expose
        private String bannerImage;
        private final static long serialVersionUID = -4513659176132624032L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getBannerTitle() {
            return bannerTitle;
        }

        public void setBannerTitle(String bannerTitle) {
            this.bannerTitle = bannerTitle;
        }

        public String getBannerDescription() {
            return bannerDescription;
        }

        public void setBannerDescription(String bannerDescription) {
            this.bannerDescription = bannerDescription;
        }

        public String getBannerImage() {
            return bannerImage;
        }

        public void setBannerImage(String bannerImage) {
            this.bannerImage = bannerImage;
        }

    }
    //
    public class List1 implements Serializable
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
        @SerializedName("category_icon")
        @Expose
        private String categoryIcon;
        @SerializedName("total_course")
        @Expose
        private Integer totalCourse;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("profile_picture")
        @Expose
        private String profilePicture;
        private final static long serialVersionUID = -6680703910659996623L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCourseOldPrice() {
            return courseOldPrice;
        }

        public void setCourseOldPrice(String courseOldPrice) {
            this.courseOldPrice = courseOldPrice;
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

        public String getCategoryIcon() {
            return categoryIcon;
        }

        public void setCategoryIcon(String categoryIcon) {
            this.categoryIcon = categoryIcon;
        }

        public Integer getTotalCourse() {
            return totalCourse;
        }

        public void setTotalCourse(Integer totalCourse) {
            this.totalCourse = totalCourse;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

    }


    }