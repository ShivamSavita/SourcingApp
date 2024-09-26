package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VillageData {
    @SerializedName("villagE_CODE")
    @Expose
    private String villagECODE;
    @SerializedName("villagE_NAME")
    @Expose
    private String villagENAME;
    @SerializedName("statE_CODE")
    @Expose
    private String statECODE;
    @SerializedName("disT_CODE")
    @Expose
    private String disTCODE;
    @SerializedName("suB_DIST_CODE")
    @Expose
    private String suBDISTCODE;

    public VillageData() {
    }

    public VillageData(String villagECODE, String villagENAME, String statECODE, String disTCODE, String suBDISTCODE) {
        this.villagECODE = villagECODE;
        this.villagENAME = villagENAME;
        this.statECODE = statECODE;
        this.disTCODE = disTCODE;
        this.suBDISTCODE = suBDISTCODE;
    }

    public String getVillagECODE() {
        return villagECODE;
    }

    public void setVillagECODE(String villagECODE) {
        this.villagECODE = villagECODE;
    }

    public String getVillagENAME() {
        return villagENAME;
    }

    public void setVillagENAME(String villagENAME) {
        this.villagENAME = villagENAME;
    }

    public String getStatECODE() {
        return statECODE;
    }

    public void setStatECODE(String statECODE) {
        this.statECODE = statECODE;
    }

    public String getDisTCODE() {
        return disTCODE;
    }

    public void setDisTCODE(String disTCODE) {
        this.disTCODE = disTCODE;
    }

    public String getSuBDISTCODE() {
        return suBDISTCODE;
    }

    public void setSuBDISTCODE(String suBDISTCODE) {
        this.suBDISTCODE = suBDISTCODE;
    }
}
