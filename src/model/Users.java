package model;

import java.io.Serializable;

public class Users implements Serializable {
    private String login;
    private String password;
    private String Role;
    private int ClientID;

    public Users() {
    }

    public Users(String login, String password, String role, int clientID) {
        this.login = login;
        this.password = password;
        Role = role;
        ClientID = clientID;
    }

    public Users (String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Users(String login, String password, int clientID) {
        this.login = login;
        this.password = password;
        Role = "user";
        ClientID = clientID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }
}