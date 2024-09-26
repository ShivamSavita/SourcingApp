package com.softeksol.paisalo.jlgsourcing.collectionreport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Emi {

    @SerializedName("amt")
    @Expose
    private Integer amt;
    @SerializedName("pvN_RCP_DT")
    @Expose
    private String pvNRCPDT;
    public Integer getAmt() {
        return amt;
    }
    public void setAmt(Integer amt) {
        this.amt = amt;
    }
    public String getPvNRCPDT() {
        return pvNRCPDT;
    }
    public void setPvNRCPDT(String pvNRCPDT) {
        this.pvNRCPDT = pvNRCPDT;
    }
}
