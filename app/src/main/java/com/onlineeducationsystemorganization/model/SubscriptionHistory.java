package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionHistory {

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

    @SerializedName("subscriptionlist")
    @Expose
    private Subscriptionlist subscriptionlist;

    public Subscriptionlist getSubscriptionlist() {
        return subscriptionlist;
    }

    public void setSubscriptionlist(Subscriptionlist subscriptionlist) {
        this.subscriptionlist = subscriptionlist;
    }

}
//
public class Subscriptionhistorylist {

    @SerializedName("inquiry_date")
    @Expose
    private String inquiryDate;
    @SerializedName("subscription_date")
    @Expose
    private String subscriptionDate;
    @SerializedName("no_of_user")
    @Expose
    private Integer noOfUser;
    @SerializedName("price")
    @Expose
    private Integer price;

    public String getInquiryDate() {
        return inquiryDate;
    }

    public void setInquiryDate(String inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    public String getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Integer getNoOfUser() {
        return noOfUser;
    }

    public void setNoOfUser(Integer noOfUser) {
        this.noOfUser = noOfUser;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
//
public class Subscriptionlist {

    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("sub_category")
    @Expose
    private String subCategory;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("subscriptionhistorylist")
    @Expose
    private ArrayList<Subscriptionhistorylist> subscriptionhistorylist = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<Subscriptionhistorylist> getSubscriptionhistorylist() {
        return subscriptionhistorylist;
    }

    public void setSubscriptionhistorylist(ArrayList<Subscriptionhistorylist> subscriptionhistorylist) {
        this.subscriptionhistorylist = subscriptionhistorylist;
    }

}
//


}