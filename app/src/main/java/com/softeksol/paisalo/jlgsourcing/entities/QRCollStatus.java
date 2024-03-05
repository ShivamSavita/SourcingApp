package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;

public class QRCollStatus {


    @Expose
    private String caseCode;
    @Expose
    private String date;
    @Expose
    private String creator;
    @Expose
    private String foCode;
    @Expose
    private String partyCd;
    @Expose
    private String collPoint;
    @Expose
    private String payFlag;
    @Expose
    private String paymentMode;
    @Expose
    private String BorrowerName;


    public String getBorrowerName() {
        return BorrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        BorrowerName = borrowerName;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getFoCode() {
        return foCode;
    }

    public void setFoCode(String foCode) {
        this.foCode = foCode;
    }

    public String getPartyCd() {
        return partyCd;
    }

    public void setPartyCd(String partyCd) {
        this.partyCd = partyCd;
    }

    public String getCollPoint() {
        return collPoint;
    }

    public void setCollPoint(String collPoint) {
        this.collPoint = collPoint;
    }

    public String getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(String payFlag) {
        this.payFlag = payFlag;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }


}
