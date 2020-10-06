package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompanyUrl {

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

    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}

}