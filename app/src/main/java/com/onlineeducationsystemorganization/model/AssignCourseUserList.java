package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AssignCourseUserList {

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

    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("users")
    @Expose
    private ArrayList<User> users = null;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

}
//

    public class User {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone_no")
        @Expose
        private String phoneNo;
        @SerializedName("is_assign")
        @Expose
        private Integer isAssign;
        @SerializedName("is_coursestart")
        @Expose
        private Integer isCoursestart;
        @SerializedName("user_delete")
        @Expose
        private Integer userDelete;

        private boolean isSelected;
        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public Integer getIsAssign() {
            return isAssign;
        }

        public void setIsAssign(Integer isAssign) {
            this.isAssign = isAssign;
        }

        public Integer getIsCoursestart() {
            return isCoursestart;
        }

        public void setIsCoursestart(Integer isCoursestart) {
            this.isCoursestart = isCoursestart;
        }

        public Integer getUserDelete() {
            return userDelete;
        }

        public void setUserDelete(Integer userDelete) {
            this.userDelete = userDelete;
        }

    }
}