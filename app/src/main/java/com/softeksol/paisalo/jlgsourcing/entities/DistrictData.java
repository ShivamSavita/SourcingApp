package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictData {

    @SerializedName("disT_CODE")
    @Expose
    private String disTCODE;
    @SerializedName("disT_NAME")
    @Expose
    private String disTNAME;
    @SerializedName("statE_CODE")
    @Expose
    private String statECODE;

    public String getDisTCODE() {
        return disTCODE;
    }

    public void setDisTCODE(String disTCODE) {
        this.disTCODE = disTCODE;
    }

    public String getDisTNAME() {
        return disTNAME;
    }

    public void setDisTNAME(String disTNAME) {
        this.disTNAME = disTNAME;
    }

    public String getStatECODE() {
        return statECODE;
    }

    public void setStatECODE(String statECODE) {
        this.statECODE = statECODE;
    }
}
