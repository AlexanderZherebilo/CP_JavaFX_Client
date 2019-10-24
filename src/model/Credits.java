package model;

import java.io.Serializable;

public class Credits implements Serializable {

    private int CreditID;
    private String CreditName;
    private int CreditMaxSumm;
    private String CurrencyID;
    private float CreditNominalRate;

    public Credits() {
    }

    public Credits(Credits cr) {
        CreditID = cr.CreditID;
        CreditName = cr.CreditName;
        CreditMaxSumm = cr.CreditMaxSumm;
        CurrencyID = cr.CurrencyID;
        CreditNominalRate = cr.CreditNominalRate;
    }

    public Credits(int creditID, String creditName, int creditMaxSumm, String currencyID, float creditNominalRate) {
        CreditID = creditID;
        CreditName = creditName;
        CreditMaxSumm = creditMaxSumm;
        CurrencyID = currencyID;
        CreditNominalRate = creditNominalRate;
    }

    public int getCreditID() {
        return CreditID;
    }

    public void setCreditID(int creditID) {
        CreditID = creditID;
    }

    public String getCreditName() {
        return CreditName;
    }

    public void setCreditName(String creditName) {
        CreditName = creditName;
    }

    public int getCreditMaxSumm() {
        return CreditMaxSumm;
    }

    public void setCreditMaxSumm(int creditMaxSumm) {
        CreditMaxSumm = creditMaxSumm;
    }

    public String getCurrencyID() {
        return CurrencyID;
    }

    public void setCurrencyID(String currencyID) {
        CurrencyID = currencyID;
    }

    public float getCreditNominalRate() {
        return CreditNominalRate;
    }

    public void setCreditNominalRate(float creditNominalRate) {
        CreditNominalRate = creditNominalRate;
    }
}
