package com.fathom.mofa.DataModels;

import android.graphics.Bitmap;

import java.util.Date;

public class DriverDataModel {

    private String driverName;
    private String driverID;
    private String addressLine1;
    private String addressLine2;
    private String nationality;
    private String phoneNumber;
    private Date issueDate;
    private Date expiryDate;
    private String frontLicense;
    private String backLicense;
    private boolean busy;
    private Bitmap licenseFront;
    private Bitmap licenseBack;


    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getFrontLicense() {
        return frontLicense;
    }

    public void setFrontLicense(String frontLicense) {
        this.frontLicense = frontLicense;
    }

    public String getBackLicense() {
        return backLicense;
    }

    public void setBackLicense(String backLicense) {
        this.backLicense = backLicense;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public Bitmap getLicenseFront() {
        return licenseFront;
    }

    public void setLicenseFront(Bitmap licenseFront) {
        this.licenseFront = licenseFront;
    }

    public Bitmap getLicenseBack() {
        return licenseBack;
    }

    public void setLicenseBack(Bitmap licenseBack) {
        this.licenseBack = licenseBack;
    }
}
