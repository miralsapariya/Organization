package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseDetail implements Serializable
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
    private final static long serialVersionUID = -4248524427018924632L;

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

    ///

    public class Datum implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("is_free")
        @Expose
        private Integer is_free;

        @SerializedName("is_added")
        @Expose
        private Integer is_added;
        @SerializedName("is_purchased")
        @Expose
        private Integer is_purchased;

        @SerializedName("course_name")
        @Expose
        private String courseName;
        @SerializedName("total_subscriber")
        @Expose
        private Integer totalSubscriber;
        @SerializedName("is_wishlist")
        @Expose
        private Integer isWishlist;
        @SerializedName("course_price")
        @Expose
        private String coursePrice;
        @SerializedName("course_old_price")
        @Expose
        private String courseOldPrice;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("course_include_title")
        @Expose
        private String courseIncludeTitle;
        @SerializedName("course_include")
        @Expose
        private ArrayList<CourseInclude> courseInclude = null;
        @SerializedName("section_title")
        @Expose
        private String sectionTitle;
        @SerializedName("section_details")
        @Expose
        private ArrayList<SectionDetail> sectionDetails = null;
        @SerializedName("created_by")
        @Expose
        private String createdBy;

        public Integer getIs_purchased() {
            return is_purchased;
        }

        public void setIs_purchased(Integer is_purchased) {
            this.is_purchased = is_purchased;
        }

        public Integer getIs_added() {
            return is_added;
        }

        public void setIs_added(Integer is_added) {
            this.is_added = is_added;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        @SerializedName("share_url")
        @Expose
        private String share_url;


        @SerializedName("instructor_details")
        @Expose
        private InstructorDetails instructorDetails;
        private final static long serialVersionUID = 136119190814254459L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getIs_free() {
            return is_free;
        }

        public void setIs_free(Integer is_free) {
            this.is_free = is_free;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public Integer getTotalSubscriber() {
            return totalSubscriber;
        }

        public void setTotalSubscriber(Integer totalSubscriber) {
            this.totalSubscriber = totalSubscriber;
        }

        public Integer getIsWishlist() {
            return isWishlist;
        }

        public void setIsWishlist(Integer isWishlist) {
            this.isWishlist = isWishlist;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCourseIncludeTitle() {
            return courseIncludeTitle;
        }

        public void setCourseIncludeTitle(String courseIncludeTitle) {
            this.courseIncludeTitle = courseIncludeTitle;
        }

        public ArrayList<CourseInclude> getCourseInclude() {
            return courseInclude;
        }

        public void setCourseInclude(ArrayList<CourseInclude> courseInclude) {
            this.courseInclude = courseInclude;
        }

        public String getSectionTitle() {
            return sectionTitle;
        }

        public void setSectionTitle(String sectionTitle) {
            this.sectionTitle = sectionTitle;
        }

        public ArrayList<SectionDetail> getSectionDetails() {
            return sectionDetails;
        }

        public void setSectionDetails(ArrayList<SectionDetail> sectionDetails) {
            this.sectionDetails = sectionDetails;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public InstructorDetails getInstructorDetails() {
            return instructorDetails;
        }

        public void setInstructorDetails(InstructorDetails instructorDetails) {
            this.instructorDetails = instructorDetails;
        }

    }

    ///
    public class CourseInclude implements Serializable
    {

        @SerializedName("include_icon")
        @Expose
        private String includeIcon;
        @SerializedName("include_title")
        @Expose
        private String includeTitle;
        private final static long serialVersionUID = -1224806418619147619L;

        public String getIncludeIcon() {
            return includeIcon;
        }

        public void setIncludeIcon(String includeIcon) {
            this.includeIcon = includeIcon;
        }

        public String getIncludeTitle() {
            return includeTitle;
        }

        public void setIncludeTitle(String includeTitle) {
            this.includeTitle = includeTitle;
        }

    }

    ///

    public class InstructorDetails implements Serializable
    {

        @SerializedName("instructor_name")
        @Expose
        private String instructorName;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("total_students")
        @Expose
        private Integer totalStudents;
        @SerializedName("total_course")
        @Expose
        private Integer totalCourse;
        private final static long serialVersionUID = -6945934332407118435L;

        public Integer getInstructor_id() {
            return instructor_id;
        }

        public void setInstructor_id(Integer instructor_id) {
            this.instructor_id = instructor_id;
        }

        @SerializedName("instructor_id")
        @Expose
        private Integer instructor_id;
        public String getInstructorName() {
            return instructorName;
        }

        public void setInstructorName(String instructorName) {
            this.instructorName = instructorName;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public Integer getTotalStudents() {
            return totalStudents;
        }

        public void setTotalStudents(Integer totalStudents) {
            this.totalStudents = totalStudents;
        }

        public Integer getTotalCourse() {
            return totalCourse;
        }

        public void setTotalCourse(Integer totalCourse) {
            this.totalCourse = totalCourse;
        }

    }
    //
    public class SectionSlideDetail implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("slide_name")
        @Expose
        private String slideName;
        @SerializedName("slide_desc")
        @Expose
        private String slideDesc;
        private final static long serialVersionUID = -1737196277854801864L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSlideName() {
            return slideName;
        }

        public void setSlideName(String slideName) {
            this.slideName = slideName;
        }

        public String getSlideDesc() {
            return slideDesc;
        }

        public void setSlideDesc(String slideDesc) {
            this.slideDesc = slideDesc;
        }

    }

    ///

    public class SectionDetail implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("section_name")
        @Expose
        private String sectionName;
        @SerializedName("section_slide_details")
        @Expose
        private List<SectionSlideDetail> sectionSlideDetails = null;
        private final static long serialVersionUID = 5907511252973440233L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public List<SectionSlideDetail> getSectionSlideDetails() {
            return sectionSlideDetails;
        }

        public void setSectionSlideDetails(List<SectionSlideDetail> sectionSlideDetails) {
            this.sectionSlideDetails = sectionSlideDetails;
        }

    }

}