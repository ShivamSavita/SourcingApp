package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;

import java.util.Comparator;
import java.util.Date;

public class PosInstRcvNew {

    @Expose
    private int InstRcvID;
    @Expose
    private String IMEI;
    @Expose
    private String caseCode;
    @Expose
    private int RcptNo;
    @Expose
    private int InstRcvAmt;
    @Expose
    private Date date;
    @Expose
    private String Flag;
    @Expose
    private Date CreationDate;
    @Expose
    private byte BatchNo;
    @Expose
    private Date BatchDate;
    @Expose
    private String foCode;
    @Expose
    private String DataBaseName;
    @Expose
    private String creator;
    @Expose
    private String CustName;
    @Expose
    private String partyCd;
    @Expose
    private String payFlag;
    @Expose
    private String SmsMobNo;
    @Expose
    private int InterestAmt;

    @Expose
    private String collPoint;
    @Expose
    private String paymentMode;
    @Expose
    private String collBranchCode;


    public String getCollBranchCode() {
        return collBranchCode;
    }

    public void setCollBranchCode(String collBranchCode) {
        this.collBranchCode = collBranchCode;
    }

    public String getCollPoint() {
        return collPoint;
    }

    public void setCollPoint(String collPoint) {
        this.collPoint = collPoint;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }


    public static Comparator<PosInstRcvNew> InstRcvName = new Comparator<PosInstRcvNew>() {
        public int compare(PosInstRcvNew dueData1, PosInstRcvNew dueData2) {

            int compareName = dueData1.CustName.compareTo(dueData2.CustName);

            return compareName;
        }

    };


    public int getInstRcvID() {
        return InstRcvID;
    }

    public void setInstRcvID(int instRcvID) {
        this. InstRcvID = instRcvID;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public int getRcptNo() {
        return RcptNo;
    }

    public void setRcptNo(int rcptNo) {
        this.RcptNo = rcptNo;
    }

    public int getInstRcvAmt() {
        return InstRcvAmt;
    }

    public void setInstRcvAmt(int instRcvAmt) {
        this.InstRcvAmt = instRcvAmt;
    }

    public Date getInstRcvDateTimeUTC() {
        return date;
    }

    public void setInstRcvDateTimeUTC(Date date) {
        this. date = date;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        this.Flag = flag;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.CreationDate = creationDate;
    }

    public byte getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(byte batchNo) {
        this.BatchNo = batchNo;
    }

    public Date getBatchDate() {
        return BatchDate;
    }

    public void setBatchDate(Date batchDate) {
        this.BatchDate = batchDate;
    }

    public String getFoCode() {
        return foCode;
    }

    public void setFoCode(String foCode) {
        this.foCode = foCode;
    }

    public String getDataBaseName() {
        return DataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        DataBaseName = dataBaseName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String CustName) {
        this. CustName = CustName;
    }

    public String getPartyCd() {
        return partyCd;
    }

    public void setPartyCd(String partyCd) {
        this.partyCd = partyCd;
    }

    public String getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(String payFlag) {
        this.payFlag = payFlag;
    }

    public String getSmsMobNo() {
        return SmsMobNo;
    }

    public void setSmsMobNo(String smsMobNo) {
        this.SmsMobNo = smsMobNo;
    }

    public int getInterestAmt() {
        return InterestAmt;
    }

    public void setInterestAmt(int interestAmt) {
        this.InterestAmt = interestAmt;
    }

    @Override
    public String toString() {
        return "PosInstRcv{" +
                "InstRcvID=" + InstRcvID +
                ", IMEI='" + IMEI + '\'' +
                ", caseCode='" + caseCode + '\'' +
                ", RcptNo=" + RcptNo +
                ", InstRcvAmt=" + InstRcvAmt +
                ", date=" + date +
                ", Flag='" + Flag + '\'' +
                ", CreationDate=" + CreationDate +
                ", BatchNo=" + BatchNo +
                ", BatchDate=" + BatchDate +
                ", foCode='" + foCode + '\'' +
                ", DataBaseName='" + DataBaseName + '\'' +
                ", creator='" + creator + '\'' +
                ", CustName='" + CustName + '\'' +
                ", partyCd='" + partyCd + '\'' +
                ", payFlag='" + payFlag + '\'' +
                ", SmsMobNo='" + SmsMobNo + '\'' +
                ", InterestAmt='" + InterestAmt + '\'' +
                '}';
    }
}


