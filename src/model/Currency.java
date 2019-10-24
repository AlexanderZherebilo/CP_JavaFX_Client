package model;

import java.io.Serializable;

public class Currency implements Serializable {
    private String CurrencyID;
    private String CurrencyName;
    private double RoundingStep;
    private float CurrencyRate;

    public Currency() {
    }

    public Currency(Currency cur) {
        CurrencyID = cur.CurrencyID;
        CurrencyName = cur.CurrencyName;
        RoundingStep = cur.RoundingStep;
        CurrencyRate = cur.CurrencyRate;
    }

    public Currency(String currencyID, String currencyName, double roundingStep, float currencyRate) {
        CurrencyID = currencyID;
        CurrencyName = currencyName;
        RoundingStep = roundingStep;
        CurrencyRate = currencyRate;
    }

    public String getCurrencyID() {
        return CurrencyID;
    }

    public void setCurrencyID(String currencyID) {
        CurrencyID = currencyID;
    }

    public String getCurrencyName() {
        return CurrencyName;
    }

    public void setCurrencyName(String currencyName) {
        CurrencyName = currencyName;
    }

    public double getRoundingStep() {
        return RoundingStep;
    }

    public void setRoundingStep(double roundingStep) {
        RoundingStep = roundingStep;
    }

    public float getCurrencyRate() {
        return CurrencyRate;
    }

    public void setCurrencyRate(float currencyRate) {
        CurrencyRate = currencyRate;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "CurrencyID='" + CurrencyID + '\'' +
                ", CurrencyName='" + CurrencyName + '\'' +
                ", RoundingStep=" + RoundingStep +
                ", CurrencyRate=" + CurrencyRate +
                '}';
    }
}