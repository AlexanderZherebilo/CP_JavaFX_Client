package model;

import java.io.Serializable;
import java.sql.Date;

public class ClientsCredits implements Serializable {
    private int ClientID;
    private int CreditID;
    private String ClientSurname;
    private String CreditName;
    private float CreditNominalRate;
    private Date CreditGiveDate;
    private Date CreditReturnDate;
    private int CreditFrequency;
    private int CreditSumm;
    private float CreditEffectiveRate;
    private float CreditTotalPayments;
    private float CreditPeicePayment;

    public ClientsCredits() {
    }

    public ClientsCredits(ClientsCredits cc) {
        ClientID = cc.ClientID;
        CreditID = cc.CreditID;
        ClientSurname = cc.ClientSurname;
        CreditName = cc.CreditName;
        CreditNominalRate = cc.CreditNominalRate;
        CreditGiveDate = cc.CreditGiveDate;
        CreditReturnDate = cc.CreditReturnDate;
        CreditFrequency = cc.CreditFrequency;
        CreditSumm = cc.CreditSumm;
        CreditEffectiveRate = cc.CreditEffectiveRate;
        CreditTotalPayments = cc.CreditTotalPayments;
        CreditPeicePayment = cc.CreditPeicePayment;
    }

    public ClientsCredits(int clientID, int creditID, String clientSurname, String creditName, float creditNominalRate,
                          Date creditGiveDate, Date creditReturnDate, int creditFrequency, int creditSumm) {
        ClientID = clientID;
        CreditID = creditID;
        ClientSurname = clientSurname;
        CreditName = creditName;
        CreditNominalRate = creditNominalRate;
        CreditGiveDate = creditGiveDate;
        CreditReturnDate = creditReturnDate;
        CreditFrequency = creditFrequency;
        CreditSumm = creditSumm;
        CreditEffectiveRate = CalculateEffectiveRate(CreditNominalRate, CreditFrequency);
        CreditTotalPayments = CalculateTotalPayments(CreditNominalRate, CreditFrequency, CreditSumm);
        CreditPeicePayment = CalculatePeicePayments(CreditTotalPayments, CreditFrequency);
    }

    public float CalculateEffectiveRate(float nr, int f) {
        float er;
        er= (float) ((Math.pow((1+(nr/100)/f), f)-1)*100);
        return er;
    }

    public float CalculateTotalPayments(float nr, int f, int s) {
        float er, tp;
        er=CalculateEffectiveRate(nr, f);
        tp=(1+er/100)*s;
        return tp;
    }

    public float CalculatePeicePayments(float tp, int f) {
        float pp;
        pp=tp/f;
        return pp;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }

    public int getCreditID() {
        return CreditID;
    }

    public void setCreditID(int creditID) {
        CreditID = creditID;
    }

    public String getClientSurname() {
        return ClientSurname;
    }

    public void setClientSurname(String clientSurname) {
        ClientSurname = clientSurname;
    }

    public String getCreditName() {
        return CreditName;
    }

    public void setCreditName(String creditName) {
        CreditName = creditName;
    }

    public float getCreditNominalRate() {
        return CreditNominalRate;
    }

    public void setCreditNominalRate(float creditNominalRate) {
        CreditNominalRate = creditNominalRate;
    }

    public int getCreditFrequency() {
        return CreditFrequency;
    }

    public void setCreditFrequency(int creditFrequency) {
        CreditFrequency = creditFrequency;
    }

    public int getCreditSumm() {
        return CreditSumm;
    }

    public void setCreditSumm(int creditSumm) {
        CreditSumm = creditSumm;
    }

    public float getCreditEffectiveRate() {
        return CreditEffectiveRate;
    }

    public void setCreditEffectiveRate(float creditEffectiveRate) {
        CreditEffectiveRate = creditEffectiveRate;
    }

    public float getCreditTotalPayments() {
        return CreditTotalPayments;
    }

    public void setCreditTotalPayments(float creditTotalPayments) {
        CreditTotalPayments = creditTotalPayments;
    }

    public float getCreditPeicePayment() {
        return CreditPeicePayment;
    }

    public void setCreditPeicePayment(float creditPeicePayment) {
        CreditPeicePayment = creditPeicePayment;
    }

    public Date getCreditGiveDate() {
        return CreditGiveDate;
    }

    public void setCreditGiveDate(Date creditGiveDate) {
        CreditGiveDate = creditGiveDate;
    }

    public Date getCreditReturnDate() {
        return CreditReturnDate;
    }

    public void setCreditReturnDate(Date creditReturnDate) {
        CreditReturnDate = creditReturnDate;
    }

    @Override
    public String toString() {
        return "ClientsCredits{" +
                "ClientID=" + ClientID +
                ", CreditID=" + CreditID +
                ", ClientSurname='" + ClientSurname + '\'' +
                ", CreditName='" + CreditName + '\'' +
                ", CreditNominalRate=" + CreditNominalRate +
                ", CreditGiveDate=" + CreditGiveDate +
                ", CreditReturnDate=" + CreditReturnDate +
                ", CreditFrequency=" + CreditFrequency +
                ", CreditSumm=" + CreditSumm +
                ", CreditEffectiveRate=" + CreditEffectiveRate +
                ", CreditTotalPayments=" + CreditTotalPayments +
                ", CreditPeicePayment=" + CreditPeicePayment +
                '}';
    }
}
