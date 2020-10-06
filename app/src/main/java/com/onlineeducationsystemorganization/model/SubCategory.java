package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubCategory implements Serializable
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
private final static long serialVersionUID = -1911461715246884859L;

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

    public class Datum implements Serializable
    {

        @SerializedName("categories")
        @Expose
        private List<Category> categories = null;
        @SerializedName("instructor_label")
        @Expose
        private String instructorLabel;
        @SerializedName("instructor_list")
        @Expose
        private final static long serialVersionUID = -2923495393502265649L;

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories(List<Category> categories) {
            this.categories = categories;
        }

        public String getInstructorLabel() {
            return instructorLabel;
        }

        public void setInstructorLabel(String instructorLabel) {
            this.instructorLabel = instructorLabel;
        }



    }

    public class Category implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("sub_categories")
        @Expose
        private ArrayList<SubCategory_> subCategories = null;
        private final static long serialVersionUID = -7379344156738656422L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public ArrayList<SubCategory_> getSubCategories() {
            return subCategories;
        }

        public void setSubCategories(ArrayList<SubCategory_> subCategories) {
            this.subCategories = subCategories;
        }

    }

    public class CourseList implements Serializable
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
        private final static long serialVersionUID = -6130330990349248618L;

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

    }
    //
    public class SubCategory_ implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("sub_category_name")
        @Expose
        private String subCategoryName;
        @SerializedName("course_list")
        @Expose
        private ArrayList<CourseList> courseList = null;
        private final static long serialVersionUID = -1930753326559395074L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public ArrayList<CourseList> getCourseList() {
            return courseList;
        }

        public void setCourseList(ArrayList<CourseList> courseList) {
            this.courseList = courseList;
        }

    }


}