package com.fathom.mofa.DataModels;

import java.util.Date;

public class RentalInfoDataModel {

    private String name;
    private String phoneNumber;
    private Date leaseFrom;
    private Date leaseTo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getLeaseFrom() {
        return leaseFrom;
    }

    public void setLeaseFrom(Date leaseFrom) {
        this.leaseFrom = leaseFrom;
    }

    public Date getLeaseTo() {
        return leaseTo;
    }

    public void setLeaseTo(Date leaseTo) {
        this.leaseTo = leaseTo;
    }
}
