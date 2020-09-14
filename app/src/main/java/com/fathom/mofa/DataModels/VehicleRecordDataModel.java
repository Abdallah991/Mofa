package com.fathom.mofa.DataModels;

import java.util.Comparator;
import java.util.Date;

public class VehicleRecordDataModel {

    private String releasePersonName;
    private String driverName;
    private String vehicleName;
    private String plateNumber;
    private String chassisNumber;
    // These photos will have value only when the car has damage
    private String photoLeftSide;
    private String photoRightSide;
    private String photoFrontSide;
    private String photoBackSide;
    private String notes;
    private String model;
    private String make;
    private String rentalInfo;
    private String carType;
    // Vehicle will have these Statuses
    // 1- Busy       -- out with a Driver    --orange
    // 2- Available   -- in Mofa position    --green
    // 3- Released   -- with Rental Company  --red
    private String status;
    private String milage;
    private int fuelLevel;
    private String damageReport;
    private Date date;
    private boolean jackStatus = false;
    private boolean toolsStatus = false;
    private boolean spareTireStatus = false;
    private boolean CLighterStatus = false;
    private boolean wheelCapStatus = false;
    private boolean floorMatStatus = false;
    private boolean carHasDamage = false;
    private boolean carIsUseable = false;
    // Car Transaction will mention what kind of record the car is:
    // 1- Getting the car from the rental company. "RTM"
    // 2- Returning the car to the rental company. "MTR"
    // 3- Hanover to the driver."MTD"
    // 4- Retrieval from the driver. "DTM"
    // D: Driver
    // M: Mofa
    // R: Rental Company
    private String carTransaction;
    private String companyReleaseSignature;
    private String mofaRetrievalSignature;
    // the name of vehicle record will be a combination of plate number and date which will be
    // identical to damage report name
    private String name;


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

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
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

    public boolean isCarHasDamage() {
        return carHasDamage;
    }

    public void setCarHasDamage(boolean carHasDamage) {
        this.carHasDamage = carHasDamage;
    }

    public boolean isCarIsUseable() {
        return carIsUseable;
    }

    public void setCarIsUseable(boolean carIsUseable) {
        this.carIsUseable = carIsUseable;
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

    public String getRentalInfo() {
        return rentalInfo;
    }

    public void setRentalInfo(String rentalInfo) {
        this.rentalInfo = rentalInfo;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    // Classes to sort Array list depending on Alphabet order
    // per status field
    public static Comparator<VehicleRecordDataModel> statusComparator = new Comparator<VehicleRecordDataModel>() {
        @Override
        public int compare(VehicleRecordDataModel jc1, VehicleRecordDataModel jc2) {
            return (int) (jc1.getStatus().compareTo(jc2.getStatus()));
        }
    };
    // per model field
    public static Comparator<VehicleRecordDataModel> modelComparator = new Comparator<VehicleRecordDataModel>() {
        @Override
        public int compare(VehicleRecordDataModel jc1, VehicleRecordDataModel jc2) {
            return (int) (jc1.getModel().compareTo(jc2.getModel()));
        }
    };
    // per rental info field
    public static Comparator<VehicleRecordDataModel> rentalComparator = new Comparator<VehicleRecordDataModel>() {
        @Override
        public int compare(VehicleRecordDataModel jc1, VehicleRecordDataModel jc2) {
            return (int) (jc1.getRentalInfo().compareTo(jc2.getRentalInfo()));
        }
    };
    // per driver name field
    public static Comparator<VehicleRecordDataModel> driverComparator = new Comparator<VehicleRecordDataModel>() {
        @Override
        public int compare(VehicleRecordDataModel jc1, VehicleRecordDataModel jc2) {
            return (int) (jc1.getDriverName().compareTo(jc2.getDriverName()));
        }
    };
    // per year of make field
    public static Comparator<VehicleRecordDataModel> makeComparator = new Comparator<VehicleRecordDataModel>() {
        @Override
        public int compare(VehicleRecordDataModel jc1, VehicleRecordDataModel jc2) {
            return (int) (jc1.getMake().compareTo(jc2.getMake()));
        }
    };

}
