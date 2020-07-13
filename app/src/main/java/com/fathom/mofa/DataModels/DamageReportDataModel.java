package com.fathom.mofa.DataModels;

public class DamageReportDataModel {

    private boolean front;
    private boolean frontRight;
    private boolean frontLeft;
    private boolean driverDoor;
    private boolean passengerDoor;
    private boolean hood;
    private boolean frontWindShield;
    private boolean backLeftDoor;
    private boolean backRightDoor;
    private boolean ceiling;
    private boolean backRight;
    private boolean backLeft;
    private boolean back;
    private boolean trunk;
    private boolean backWindShield;
    private String carId;
    // To describe the transition of the car which is four Types
    // 1- Getting the car from the rental company.
    // 2- Returning the car to the rental company.
    // 3- Hanover to the driver.
    // 4- Retrieval from the driver
    private String carTransaction;


    // to initialize the damage report with no errors
    public DamageReportDataModel() {

        this.front = false;
        this.frontRight = false;
        this.frontLeft = false;
        this.driverDoor = false;
        this.passengerDoor = false;
        this.hood = false;
        this.frontWindShield = false;
        this.backLeftDoor = false;
        this.backRightDoor = false;
        this.ceiling = false;
        this.backRight = false;
        this.backLeft = false;
        this.back = false;
        this.trunk = false;
        this.backWindShield = false;
    }

    public DamageReportDataModel(boolean front, boolean frontRight, boolean frontLeft, boolean driverDoor, boolean passengerDoor, boolean hood, boolean frontWindShield, boolean backLeftDoor, boolean backRightDoor, boolean ceiling, boolean backRight, boolean backLeft, boolean back, boolean trunk, boolean backWindShield) {
        this.front = front;
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
        this.driverDoor = driverDoor;
        this.passengerDoor = passengerDoor;
        this.hood = hood;
        this.frontWindShield = frontWindShield;
        this.backLeftDoor = backLeftDoor;
        this.backRightDoor = backRightDoor;
        this.ceiling = ceiling;
        this.backRight = backRight;
        this.backLeft = backLeft;
        this.back = back;
        this.trunk = trunk;
        this.backWindShield = backWindShield;
    }

    public boolean isFront() {
        return front;
    }

    public void setFront(boolean front) {
        this.front = front;
    }

    public boolean isFrontRight() {
        return frontRight;
    }

    public void setFrontRight(boolean frontRight) {
        this.frontRight = frontRight;
    }

    public boolean isFrontLeft() {
        return frontLeft;
    }

    public void setFrontLeft(boolean frontLeft) {
        this.frontLeft = frontLeft;
    }

    public boolean isDriverDoor() {
        return driverDoor;
    }

    public void setDriverDoor(boolean driverDoor) {
        this.driverDoor = driverDoor;
    }

    public boolean isPassengerDoor() {
        return passengerDoor;
    }

    public void setPassengerDoor(boolean passengerDoor) {
        this.passengerDoor = passengerDoor;
    }

    public boolean isHood() {
        return hood;
    }

    public void setHood(boolean hood) {
        this.hood = hood;
    }

    public boolean isFrontWindShield() {
        return frontWindShield;
    }

    public void setFrontWindShield(boolean frontWindShield) {
        this.frontWindShield = frontWindShield;
    }

    public boolean isBackLeftDoor() {
        return backLeftDoor;
    }

    public void setBackLeftDoor(boolean backLeftDoor) {
        this.backLeftDoor = backLeftDoor;
    }

    public boolean isBackRightDoor() {
        return backRightDoor;
    }

    public void setBackRightDoor(boolean backRightDoor) {
        this.backRightDoor = backRightDoor;
    }

    public boolean isCeiling() {
        return ceiling;
    }

    public void setCeiling(boolean ceiling) {
        this.ceiling = ceiling;
    }

    public boolean isBackRight() {
        return backRight;
    }

    public void setBackRight(boolean backRight) {
        this.backRight = backRight;
    }

    public boolean isBackLeft() {
        return backLeft;
    }

    public void setBackLeft(boolean backLeft) {
        this.backLeft = backLeft;
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public boolean isTrunk() {
        return this.trunk;
    }

    public void setTrunk(boolean trunk) {
        this.trunk = trunk;
    }

    public boolean isBackWindShield() {
        return backWindShield;
    }

    public void setBackWindShield(boolean backWindShield) {
        this.backWindShield = backWindShield;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarTransaction() {
        return carTransaction;
    }

    public void setCarTransaction(String carTransaction) {
        this.carTransaction = carTransaction;
    }
}
