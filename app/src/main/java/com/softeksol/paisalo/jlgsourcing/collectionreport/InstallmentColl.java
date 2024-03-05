package com.softeksol.paisalo.jlgsourcing.collectionreport;

public class InstallmentColl {

    private String amt;
    private String Date;

    public InstallmentColl(String amt, String date) {
        this.amt = amt;
        Date = date;
    }

    public InstallmentColl() {
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
