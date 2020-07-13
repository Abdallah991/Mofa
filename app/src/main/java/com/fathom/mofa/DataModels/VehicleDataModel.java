package com.fathom.mofa.DataModels;

import android.graphics.Bitmap;

import java.util.Date;

public class VehicleDataModel {

    private String plateNumber;
    private String carName;
    private String manufacturer;
    private String model;
    private String make;
    private String carType;
    private String colorOfCar;
    private String registrationType;
    private String rentalInfo;
    private String damageReport;
    private String vehicleRecord;
    private Bitmap photoLeftSide;
    private Bitmap photoRightSide;
    private Bitmap photoFrontSide;
    private Bitmap photoBackSide;
    private Date registrationStart;
    private Date registrationEnd;
    private String notes;
    private boolean status;

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }


    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getRentalInfo() {
        return rentalInfo;
    }

    public void setRentalInfo(String rentalInfo) {
        this.rentalInfo = rentalInfo;
    }

    public String getDamageReport() {
        return damageReport;
    }

    public void setDamageReport(String damageReport) {
        this.damageReport = damageReport;
    }

    public String getVehicleRecord() {
        return vehicleRecord;
    }

    public void setVehicleRecord(String vehicleRecord) {
        this.vehicleRecord = vehicleRecord;
    }

    public Date getRegistrationStart() {
        return registrationStart;
    }

    public void setRegistrationStart(Date registrationStart) {
        this.registrationStart = registrationStart;
    }

    public Date getRegistrationEnd() {
        return registrationEnd;
    }

    public void setRegistrationEnd(Date registrationEnd) {
        this.registrationEnd = registrationEnd;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getColorOfCar() {
        return colorOfCar;
    }

    public void setColorOfCar(String colorOfCar) {
        this.colorOfCar = colorOfCar;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Bitmap getPhotoLeftSide() {
        return photoLeftSide;
    }

    public void setPhotoLeftSide(Bitmap photoLeftSide) {
        this.photoLeftSide = photoLeftSide;
    }

    public Bitmap getPhotoRightSide() {
        return photoRightSide;
    }

    public void setPhotoRightSide(Bitmap photoRightSide) {
        this.photoRightSide = photoRightSide;
    }

    public Bitmap getPhotoFrontSide() {
        return photoFrontSide;
    }

    public void setPhotoFrontSide(Bitmap photoFrontSide) {
        this.photoFrontSide = photoFrontSide;
    }

    public Bitmap getPhotoBackSide() {
        return photoBackSide;
    }

    public void setPhotoBackSide(Bitmap photoBackSide) {
        this.photoBackSide = photoBackSide;
    }
}
