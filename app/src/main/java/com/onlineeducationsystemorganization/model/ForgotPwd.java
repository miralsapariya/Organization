package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ForgotPwd implements Serializable
{

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private Data data;
private final static long serialVersionUID = -6372442362562860258L;

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

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

    public class Data implements Serializable
    {

        @SerializedName("user_id")
        @Expose
        private Integer userId;
        private final static long serialVersionUID = 3531075291896545243L;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

    }

}