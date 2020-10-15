package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SectionCourse {

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
    public class SectionSlideDetail {

        @SerializedName("slide_id")
        @Expose
        private Integer slideId;
        @SerializedName("slide_name")
        @Expose
        private String slideName;

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        @SerializedName("is_read")
        @Expose
        private String is_read;


        public Integer getSlideId() {
            return slideId;
        }

        public void setSlideId(Integer slideId) {
            this.slideId = slideId;
        }

        public String getSlideName() {
            return slideName;
        }

        public void setSlideName(String slideName) {
            this.slideName = slideName;
        }
    }

        //
        public class Datum {

            @SerializedName("section_title")
            @Expose
            private String sectionTitle;
            @SerializedName("totalsections")
            @Expose
            private String totalsections;
            @SerializedName("courseslist")
            @Expose
            private ArrayList<Courseslist> courseslist = null;

            public String getSectionTitle() {
                return sectionTitle;
            }

            public void setSectionTitle(String sectionTitle) {
                this.sectionTitle = sectionTitle;
            }

            public String getTotalsections() {
                return totalsections;
            }

            public void setTotalsections(String totalsections) {
                this.totalsections = totalsections;
            }

            public ArrayList<Courseslist> getCourseslist() {
                return courseslist;
            }

            public void setCourseslist(ArrayList<Courseslist> courseslist) {
                this.courseslist = courseslist;
            }

        }

        //
        public class Courseslist {

            @SerializedName("section_id")
            @Expose
            private Integer sectionId;
            @SerializedName("section_name")
            @Expose
            private String sectionName;
            @SerializedName("section_slide_details")
            @Expose
            private List<SectionSlideDetail> sectionSlideDetails = null;

            public Integer getSectionId() {
                return sectionId;
            }

            public void setSectionId(Integer sectionId) {
                this.sectionId = sectionId;
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

        //


}