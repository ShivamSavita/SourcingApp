package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubDistrictData {

    @SerializedName("suB_DIST_CODE")
    @Expose
    private String suBDISTCODE;
    @SerializedName("suB_DIST_NAME")
    @Expose
    private String suBDISTNAME;
    @SerializedName("disT_CODE")
    @Expose
    private String disTCODE;

    public SubDistrictData() {
    }

    public SubDistrictData(String suBDISTCODE, String suBDISTNAME, String disTCODE) {
        this.suBDISTCODE = suBDISTCODE;
        this.suBDISTNAME = suBDISTNAME;
        this.disTCODE = disTCODE;
    }

    public String getSuBDISTCODE() {
        return suBDISTCODE;
    }

    public void setSuBDISTCODE(String suBDISTCODE) {
        this.suBDISTCODE = suBDISTCODE;
    }

    public String getSuBDISTNAME() {
        return suBDISTNAME;
    }

    public void setSuBDISTNAME(String suBDISTNAME) {
        this.suBDISTNAME = suBDISTNAME;
    }

    public String getDisTCODE() {
        return disTCODE;
    }

    public void setDisTCODE(String disTCODE) {
        this.disTCODE = disTCODE;
    }

}
