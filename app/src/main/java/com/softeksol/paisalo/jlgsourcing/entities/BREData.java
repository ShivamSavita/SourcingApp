package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BREData {

    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("calculateEMI")
    @Expose
    private Integer calculateEMI;
    @SerializedName("scoreValue")
    @Expose
    private Integer scoreValue;
    @SerializedName("loanApply")
    @Expose
    private Integer loanApply;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCalculateEMI() {
        return calculateEMI;
    }

    public void setCalculateEMI(Integer calculateEMI) {
        this.calculateEMI = calculateEMI;
    }

    public Integer getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Integer scoreValue) {
        this.scoreValue = scoreValue;
    }

    public Integer getLoanApply() {
        return loanApply;
    }

    public void setLoanApply(Integer loanApply) {
        this.loanApply = loanApply;
    }
}
