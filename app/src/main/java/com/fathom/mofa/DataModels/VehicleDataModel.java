package com.fathom.mofa.DataModels;


import java.util.Date;

public class VehicleDataModel {

    private String plateNumber;
    private String chassisNumber;
    private String motorSize;
    private String carName;
    private String manufacturer;
    private String model;
    private String make;
    private String carType;
    private String colorOfCar;
    private String registrationType;
    private String rentalInfo;
    private String rentalInfoContent;
    // This will be the damage report when SETTING UP the car and when RELEASING it
    private String damageReport;
    private String vehicleRecord;
    // These are the photos when the car get set up.
    private String photoLeftSide;
    private String photoRightSide;
    private String photoFrontSide;
    private String photoBackSide;
    private String vehicleFrontInterior;
    private String vehicleBackInterior;
    private String vehicleTrunk;
    private Date registrationStart;
    private Date registrationEnd;
    private String notes;
    private String status;
    private String companyReleaseSignature;
    private String mofaRetrievalSignature;
    private Date recordDate;


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

    public String getStatus() {
        return status;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getRentalInfoContent() {
        return rentalInfoContent;
    }

    public void setRentalInfoContent(String rentalInfoContent) {
        this.rentalInfoContent = rentalInfoContent;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getMotorSize() {
        return motorSize;
    }

    public void setMotorSize(String motorSize) {
        this.motorSize = motorSize;
    }

    public String getVehicleFrontInterior() {
        return vehicleFrontInterior;
    }

    public void setVehicleFrontInterior(String vehicleFrontInterior) {
        this.vehicleFrontInterior = vehicleFrontInterior;
    }

    public String getVehicleBackInterior() {
        return vehicleBackInterior;
    }

    public void setVehicleBackInterior(String vehicleBackInterior) {
        this.vehicleBackInterior = vehicleBackInterior;
    }

    public String getVehicleTrunk() {
        return vehicleTrunk;
    }

    public void setVehicleTrunk(String vehicleTrunk) {
        this.vehicleTrunk = vehicleTrunk;
    }
}
