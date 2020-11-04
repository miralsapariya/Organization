package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class InstructorList {

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

    public class PopularInstructorList {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("total_course")
        @Expose
        private Integer totalCourse;
        @SerializedName("total_students")
        @Expose
        private Integer totalStudents;
        @SerializedName("profile_picture")
        @Expose
        private String profilePicture;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public Integer getTotalCourse() {
            return totalCourse;
        }

        public void setTotalCourse(Integer totalCourse) {
            this.totalCourse = totalCourse;
        }

        public Integer getTotalStudents() {
            return totalStudents;
        }

        public void setTotalStudents(Integer totalStudents) {
            this.totalStudents = totalStudents;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

    }
    //
    public class Datum {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("popular_instructor_list")
        @Expose
        private ArrayList<PopularInstructorList> popularInstructorList = null;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ArrayList<PopularInstructorList> getPopularInstructorList() {
            return popularInstructorList;
        }

        public void setPopularInstructorList(ArrayList<PopularInstructorList> popularInstructorList) {
            this.popularInstructorList = popularInstructorList;
        }

    }

}