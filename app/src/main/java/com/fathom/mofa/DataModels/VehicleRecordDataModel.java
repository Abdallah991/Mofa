package com.fathom.mofa.DataModels;

import java.util.Date;

public class VehicleRecordDataModel {

    private String releasePersonName;
    private String driverName;
    private String vehicleName;
    private String plateNumber;
    private String photoLeftSide;
    private String photoRightSide;
    private String photoFrontSide;
    private String photoBackSide;
    private String notes;
    private String status;
    private String milage;
    private int fuelLevel;
    private String damageReport;
    private String time;
    private Date date;
    private boolean jackStatus;
    private boolean toolsStatus;
    private boolean spareTireStatus;
    private boolean CLighterStatus;
    private boolean wheelCapStatus;
    private boolean floorMatStatus;
    // Status will mention what kind of record the car is:
    // 1- Getting the car from the rental company. "RTM"
    // 2- Returning the car to the rental company. "MTR"
    // 3- Hanover to the driver."MTD"
    // 4- Retrieval from the driver. "DTM"
    // D: Driver
    // M: Mofa
    // R: Rental Company
    private String carTransaction;

    public String getReleasePersonName() {
        return releasePersonName;
    }

    public void setReleasePersonName(String releasePersonName) {
        this.releasePersonName = releasePersonName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMilage() {
        return milage;
    }

    public void setMilage(String milage) {
        this.milage = milage;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public String getDamageReport() {
        return damageReport;
    }

    public void setDamageReport(String damageReport) {
        this.damageReport = damageReport;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCarTransaction() {
        return carTransaction;
    }

    public void setCarTransaction(String carTransaction) {
        this.carTransaction = carTransaction;
    }

    public boolean isJackStatus() {
        return jackStatus;
    }

    public void setJackStatus(boolean jackStatus) {
        this.jackStatus = jackStatus;
    }

    public boolean isToolsStatus() {
        return toolsStatus;
    }

    public void setToolsStatus(boolean toolsStatus) {
        this.toolsStatus = toolsStatus;
    }

    public boolean isSpareTireStatus() {
        return spareTireStatus;
    }

    public void setSpareTireStatus(boolean spareTireStatus) {
        this.spareTireStatus = spareTireStatus;
    }

    public boolean isCLighterStatus() {
        return CLighterStatus;
    }

    public void setCLighterStatus(boolean CLighterStatus) {
        this.CLighterStatus = CLighterStatus;
    }

    public boolean isWheelCapStatus() {
        return wheelCapStatus;
    }

    public void setWheelCapStatus(boolean wheelCapStatus) {
        this.wheelCapStatus = wheelCapStatus;
    }

    public boolean isFloorMatStatus() {
        return floorMatStatus;
    }

    public void setFloorMatStatus(boolean floorMatStatus) {
        this.floorMatStatus = floorMatStatus;
    }
}
