package com.softeksol.paisalo.jlgsourcing.entities;

import java.util.Date;

public class SmCode_DateModel {

    String smcode;

    String tranDate;

    public SmCode_DateModel(String smcode, String tranDate) {
        this.smcode = smcode;
        this.tranDate = tranDate;
    }

    public String getSmcode() {
        return smcode;
    }

    public void setSmcode(String smcode) {
        this.smcode = smcode;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }
}
