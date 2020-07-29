package com.fathom.mofa.DataModels;


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
    private String photoLeftSide;
    private String photoRightSide;
    private String photoFrontSide;
    private String photoBackSide;
    private Date registrationStart;
    private Date registrationEnd;
    private String notes;
    private String status;
    private String companyReleaseSignature;
    private String mofaRetrievalSignature;

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

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getPhotoLeftSide() {
        return photoLeftSide;
    }

    public void setPhotoLeftSide(String photoLeftSide) {
        this.photoLeftSide = photoLeftSide;
    }

    public String getPhotoRightSide() {
        return photoRightSide;
    }

    public void setPhotoRightSide(String photoRightSide) {
        this.photoRightSide = photoRightSide;
    }

    public String getPhotoFrontSide() {
        return photoFrontSide;
    }

    public void setPhotoFrontSide(String photoFrontSide) {
        this.photoFrontSide = photoFrontSide;
    }

    public String getPhotoBackSide() {
        return photoBackSide;
    }

    public void setPhotoBackSide(String photoBackSide) {
        this.photoBackSide = photoBackSide;
    }

    public String getCompanyReleaseSignature() {
        return companyReleaseSignature;
    }

    public void setCompanyReleaseSignature(String companyReleaseSignature) {
        this.companyReleaseSignature = companyReleaseSignature;
    }

    public String getMofaRetrievalSignature() {
        return mofaRetrievalSignature;
    }

    public void setMofaRetrievalSignature(String mofaRetrievalSignature) {
        this.mofaRetrievalSignature = mofaRetrievalSignature;
    }
}
