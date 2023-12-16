package com.softeksol.paisalo.jlgsourcing.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QrDataModel{
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("txnId")
    @Expose
    private String txnId;
    @SerializedName("CreationDate")
    @Expose
    private String creationDate;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

}

