package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SectionSlideDetail {

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

    public class SectionSlideDetails {

        @SerializedName("slide_id")
        @Expose
        private Integer slideId;
        @SerializedName("slide_background_image")
        @Expose
        private String slideBackgroundImage;
        @SerializedName("slide_name")
        @Expose
        private String slideName;
        @SerializedName("slide_desc")
        @Expose
        private String slideDesc;
        @SerializedName("slide_image")
        @Expose
        private String slideImage;
        @SerializedName("video_url")
        @Expose
        private String videoUrl;
        @SerializedName("document_url")
        @Expose
        private String documentUrl;

        public Integer getSlideId() {
            return slideId;
        }

        public void setSlideId(Integer slideId) {
            this.slideId = slideId;
        }

        public String getSlideBackgroundImage() {
            return slideBackgroundImage;
        }

        public void setSlideBackgroundImage(String slideBackgroundImage) {
            this.slideBackgroundImage = slideBackgroundImage;
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

        public String getSlideImage() {
            return slideImage;
        }

        public void setSlideImage(String slideImage) {
            this.slideImage = slideImage;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getDocumentUrl() {
            return documentUrl;
        }

        public void setDocumentUrl(String documentUrl) {
            this.documentUrl = documentUrl;
        }

    }
    //
    public class Datum {

        @SerializedName("course_title")
        @Expose
        private String courseTitle;
        @SerializedName("section_title")
        @Expose
        private String sectionTitle;
        @SerializedName("section_slide_details")
        @Expose
        private SectionSlideDetails sectionSlideDetails=null;
        @SerializedName("previous_section")
        @Expose
        private String previousSection;
        @SerializedName("previous_slide")
        @Expose
        private String previousSlide;
        @SerializedName("next_section")
        @Expose
        private String nextSection;
        @SerializedName("next_slide")
        @Expose
        private String nextSlide;

        public Integer getIs_completed() {
            return is_completed;
        }

        public void setIs_completed(Integer is_completed) {
            this.is_completed = is_completed;
        }

        @SerializedName("is_completed")
        @Expose
        private Integer is_completed;


        public String getCourseTitle() {
            return courseTitle;
        }

        public void setCourseTitle(String courseTitle) {
            this.courseTitle = courseTitle;
        }

        public String getSectionTitle() {
            return sectionTitle;
        }

        public void setSectionTitle(String sectionTitle) {
            this.sectionTitle = sectionTitle;
        }

        public SectionSlideDetails getSectionSlideDetails() {
            return sectionSlideDetails;
        }

        public void setSectionSlideDetails(SectionSlideDetails sectionSlideDetails) {
            this.sectionSlideDetails = sectionSlideDetails;
        }

        public String getPreviousSection() {
            return previousSection;
        }

        public void setPreviousSection(String previousSection) {
            this.previousSection = previousSection;
        }

        public String getPreviousSlide() {
            return previousSlide;
        }

        public void setPreviousSlide(String previousSlide) {
            this.previousSlide = previousSlide;
        }

        public String getNextSection() {
            return nextSection;
        }

        public void setNextSection(String nextSection) {
            this.nextSection = nextSection;
        }

        public String getNextSlide() {
            return nextSlide;
        }

        public void setNextSlide(String nextSlide) {
            this.nextSlide = nextSlide;
        }

    }
}