package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private ArrayList<Datum> data = null;
private final static long serialVersionUID = 3304260488356748443L;

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

public ArrayList<Datum> getData() {
return data;
}

public void setData(ArrayList<Datum> data) {
this.data = data;
}


    public class Datum implements Serializable
    {

        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("profile_picture")
        @Expose
        private String profilePicture;
        @SerializedName("phone_no")
        @Expose
        private String phoneNo;
        @SerializedName("token")
        @Expose
        private String token;

        @SerializedName("organization_name")
        @Expose
        private String organization_name;

        @SerializedName("subdomain")
        @Expose
        private String subdomain;

        public String getOrganization_name() {
            return organization_name;
        }

        public void setOrganization_name(String organization_name) {
            this.organization_name = organization_name;
        }

        public String getSubdomain() {
            return subdomain;
        }

        public void setSubdomain(String subdomain) {
            this.subdomain = subdomain;
        }

        private final static long serialVersionUID = 1700553466652003702L;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }

}