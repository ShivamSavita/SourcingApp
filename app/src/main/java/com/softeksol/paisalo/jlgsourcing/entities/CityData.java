package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityData {

    @SerializedName("citY_CODE")
    @Expose
    private String citYCODE;
    @SerializedName("citY_NAME")
    @Expose
    private String citYNAME;
    @SerializedName("statE_CODE")
    @Expose
    private String statECODE;

    public String getCitYCODE() {
        return citYCODE;
    }

    public void setCitYCODE(String citYCODE) {
        this.citYCODE = citYCODE;
    }

    public String getCitYNAME() {
        return citYNAME;
    }

    public void setCitYNAME(String citYNAME) {
        this.citYNAME = citYNAME;
    }

    public String getStatECODE() {
        return statECODE;
    }

    public void setStatECODE(String statECODE) {
        this.statECODE = statECODE;
    }
}
