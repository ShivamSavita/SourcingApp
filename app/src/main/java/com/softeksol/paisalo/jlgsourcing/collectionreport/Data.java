package com.softeksol.paisalo.jlgsourcing.collectionreport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("emis")
    @Expose
    private List<Emi> emis;
    @SerializedName("emiCollections")
    @Expose
    private List<EmiCollection> emiCollections;

    public List<Emi> getEmis() {
        return emis;
    }
    public void setEmis(List<Emi> emis) {
        this.emis = emis;
    }
    public List<EmiCollection> getEmiCollections() {
        return emiCollections;
    }
    public void setEmiCollections(List<EmiCollection> emiCollections) {
        this.emiCollections = emiCollections;
    }
}
