package model;

import java.io.Serializable;
import java.sql.Date;

public class Clients implements Serializable {

    private int ClientID;
    private String ClientSurname;
    private String ClientName;
    private String ClientLastname;
    private Date ClientBirthday;
    private String ClientCity;
    private String ClientStreet;
    private int ClientHouse;
    private int ClientFlat;
    private float ClientSalary;
    private String CurrencyID;

    public Clients() {
    }

    public Clients (Clients cl) {
        ClientID = cl.ClientID;
        ClientSurname = cl.ClientSurname;
        ClientName = cl.ClientName;
        ClientLastname = cl.ClientLastname;
        ClientBirthday = cl.ClientBirthday;
        ClientCity = cl.ClientCity;
        ClientStreet = cl.ClientStreet;
        ClientHouse = cl.ClientHouse;
        ClientFlat = cl.ClientFlat;
        ClientSalary = cl.ClientSalary;
        CurrencyID = cl.CurrencyID;
    }

    public Clients(int clientID, String clientSurname, String clientName,
                   String clientLastname, Date clientBirthday, String clientCity,
                   String clientStreet, int clientHouse, int clientFlat,
                   float clientSalary, String currencyID) {
        ClientID = clientID;
        ClientSurname = clientSurname;
        ClientName = clientName;
        ClientLastname = clientLastname;
        ClientBirthday = clientBirthday;
        ClientCity = clientCity;
        ClientStreet = clientStreet;
        ClientHouse = clientHouse;
        ClientFlat = clientFlat;
        ClientSalary = clientSalary;
        CurrencyID = currencyID;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }

    public String getClientSurname() {
        return ClientSurname;
    }

    public void setClientSurname(String clientSurname) {
        ClientSurname = clientSurname;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getClientLastname() {
        return ClientLastname;
    }

    public void setClientLastname(String clientLastname) {
        ClientLastname = clientLastname;
    }

    public Date getClientBirthday() {
        return ClientBirthday;
    }

    public void setClientBirthday(Date clientBirthday) {
        ClientBirthday = clientBirthday;
    }

    public String getClientCity() {
        return ClientCity;
    }

    public void setClientCity(String clientCity) {
        ClientCity = clientCity;
    }

    public String getClientStreet() {
        return ClientStreet;
    }

    public void setClientStreet(String clientStreet) {
        ClientStreet = clientStreet;
    }

    public int getClientHouse() {
        return ClientHouse;
    }

    public void setClientHouse(int clientHouse) {
        ClientHouse = clientHouse;
    }

    public int getClientFlat() {
        return ClientFlat;
    }

    public void setClientFlat(int clientFlat) {
        ClientFlat = clientFlat;
    }

    public float getClientSalary() {
        return ClientSalary;
    }

    public void setClientSalary(float clientSalary) {
        ClientSalary = clientSalary;
    }

    public String getCurrencyID() {
        return CurrencyID;
    }

    public void setCurrencyID(String currencyID) {
        CurrencyID = currencyID;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "ClientID=" + ClientID +
                ", ClientSurname='" + ClientSurname + '\'' +
                ", ClientName='" + ClientName + '\'' +
                ", ClientLastname='" + ClientLastname + '\'' +
                ", ClientBirthday=" + ClientBirthday +
                ", ClientCity='" + ClientCity + '\'' +
                ", ClientStreet='" + ClientStreet + '\'' +
                ", ClientHouse=" + ClientHouse +
                ", ClientFlat=" + ClientFlat +
                ", ClientSalary=" + ClientSalary +
                ", CurrencyID='" + CurrencyID + '\'' +
                '}';
    }
}
