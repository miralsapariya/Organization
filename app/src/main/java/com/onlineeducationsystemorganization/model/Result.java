package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

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

    public class Datum {

        @SerializedName("quiz_result_id")
        @Expose
        private Integer quizResultId;
        @SerializedName("congratulation_message")
        @Expose
        private String congratulationMessage;
        @SerializedName("pass_message")
        @Expose
        private String passMessage;

        @SerializedName("is_pass")
        @Expose
        private String is_pass;


        @SerializedName("download_certificate_btn")
        @Expose
        private String downloadCertificateBtn;
        @SerializedName("try_again_btn")
        @Expose
        private String tryAgainBtn;
        @SerializedName("certificate_link")
        @Expose
        private String certificateLink;

        public String getIs_pass() {
            return is_pass;
        }

        public void setIs_pass(String is_pass) {
            this.is_pass = is_pass;
        }

        public Integer getQuizResultId() {
            return quizResultId;
        }

        public void setQuizResultId(Integer quizResultId) {
            this.quizResultId = quizResultId;
        }

        public String getCongratulationMessage() {
            return congratulationMessage;
        }

        public void setCongratulationMessage(String congratulationMessage) {
            this.congratulationMessage = congratulationMessage;
        }

        public String getPassMessage() {
            return passMessage;
        }

        public void setPassMessage(String passMessage) {
            this.passMessage = passMessage;
        }

        public String getDownloadCertificateBtn() {
            return downloadCertificateBtn;
        }

        public void setDownloadCertificateBtn(String downloadCertificateBtn) {
            this.downloadCertificateBtn = downloadCertificateBtn;
        }

        public String getTryAgainBtn() {
            return tryAgainBtn;
        }

        public void setTryAgainBtn(String tryAgainBtn) {
            this.tryAgainBtn = tryAgainBtn;
        }

        public String getCertificateLink() {
            return certificateLink;
        }

        public void setCertificateLink(String certificateLink) {
            this.certificateLink = certificateLink;
        }

    }

}