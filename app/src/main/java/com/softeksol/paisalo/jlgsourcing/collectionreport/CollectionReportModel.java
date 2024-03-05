package com.softeksol.paisalo.jlgsourcing.collectionreport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softeksol.paisalo.jlgsourcing.retrofit.CheckCrifData;

public class CollectionReportModel{

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    public Integer getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
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


}
