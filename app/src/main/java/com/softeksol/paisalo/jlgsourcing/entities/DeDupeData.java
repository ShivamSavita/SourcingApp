package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeDupeData {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("Creator")
    @Expose
    private String creator;
    @SerializedName("SmCode")
    @Expose
    private String smCode;
    @SerializedName("Loan_Duration")
    @Expose
    private String loanDuration;
    @SerializedName("SanctionedAmt")
    @Expose
    private Integer sanctionedAmt;
    @SerializedName("Dt_Fin")
    @Expose
    private String dtFin;
    @SerializedName("CountEmi")
    @Expose
    private String countEmi;
    @SerializedName("EmiAmount")
    @Expose
    private String emiAmount;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSmCode() {
        return smCode;
    }

    public void setSmCode(String smCode) {
        this.smCode = smCode;
    }

    public String getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(String loanDuration) {
        this.loanDuration = loanDuration;
    }

    public Integer getSanctionedAmt() {
        return sanctionedAmt;
    }

    public void setSanctionedAmt(Integer sanctionedAmt) {
        this.sanctionedAmt = sanctionedAmt;
    }

    public String getDtFin() {
        return dtFin;
    }

    public void setDtFin(String dtFin) {
        this.dtFin = dtFin;
    }

    public String getCountEmi() {
        return countEmi;
    }

    public void setCountEmi(String countEmi) {
        this.countEmi = countEmi;
    }

    public String getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(String emiAmount) {
        this.emiAmount = emiAmount;
    }
}
