package com.fathom.mofa;

import com.fathom.mofa.DataModels.VehicleRecordDataModel;

import java.util.ArrayList;
import java.util.Collections;

public class VehicleRecordSorter {

    ArrayList<VehicleRecordDataModel> vehicleRecordSorted = new ArrayList<>();
    public VehicleRecordSorter(ArrayList<VehicleRecordDataModel> jobCandidate) {
        this.vehicleRecordSorted = jobCandidate;
    }
    public ArrayList<VehicleRecordDataModel> getSortedJobCandidateByStatus() {
        Collections.sort(vehicleRecordSorted, VehicleRecordDataModel.statusComparator);
        return vehicleRecordSorted;
    }

    public ArrayList<VehicleRecordDataModel> getSortedJobCandidateByMake() {
        Collections.sort(vehicleRecordSorted, VehicleRecordDataModel.makeComparator);
        return vehicleRecordSorted;
    }

    public ArrayList<VehicleRecordDataModel> getSortedJobCandidateByDriver() {
        Collections.sort(vehicleRecordSorted, VehicleRecordDataModel.driverComparator);
        return vehicleRecordSorted;
    }

    public ArrayList<VehicleRecordDataModel> getSortedJobCandidateByRental() {
        Collections.sort(vehicleRecordSorted, VehicleRecordDataModel.rentalComparator);
        return vehicleRecordSorted;
    }

    public ArrayList<VehicleRecordDataModel> getSortedJobCandidateByModel() {
        Collections.sort(vehicleRecordSorted, VehicleRecordDataModel.modelComparator);
        return vehicleRecordSorted;
    }


}
