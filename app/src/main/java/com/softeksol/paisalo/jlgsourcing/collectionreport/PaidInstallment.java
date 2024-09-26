package com.softeksol.paisalo.jlgsourcing.collectionreport;

public class PaidInstallment {

    private String PAmt;
    private String PDate;

    public PaidInstallment() {
    }

    public PaidInstallment(String PAmt, String PDate) {
        this.PAmt = PAmt;
        this.PDate = PDate;
    }

    public String getPAmt() {
        return PAmt;
    }

    public void setPAmt(String PAmt) {
        this.PAmt = PAmt;
    }

    public String getPDate() {
        return PDate;
    }

    public void setPDate(String PDate) {
        this.PDate = PDate;
    }
}
