package com.softeksol.paisalo.jlgsourcing.collectionreport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmiCollection {

    @SerializedName("cr")
    @Expose
    private Integer cr;
    @SerializedName("vdate")
    @Expose
    private String vdate;
    public Integer getCr() {
        return cr;
    }
    public void setCr(Integer cr) {
        this.cr = cr;
    }
    public String getVdate() {
        return vdate;
    }
    public void setVdate(String vdate) {
        this.vdate = vdate;
    }
}
