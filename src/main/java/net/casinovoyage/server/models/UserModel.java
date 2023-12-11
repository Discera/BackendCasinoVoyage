package net.casinovoyage.server.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;
    private String email;
    private Date dateOfBirth;
    private boolean isVerified;
    private double balance;
    private String pwHash;
    private String salt;

    public UserModel() {
    }

    public UserModel(UUID id, String username, String email, Date dateOfBirth, boolean isVerified, double balance, String pwHash, String salt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.isVerified = isVerified;
        this.balance = balance;
        this.pwHash = pwHash;
        this.salt = salt;
    }

    public UserModel(String username, String email, Date dateOfBirth, boolean isVerified, double balance, String pwHash, String salt) {
        this.username = username;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.isVerified = isVerified;
        this.balance = balance;
        this.pwHash = pwHash;
        this.salt = salt;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
