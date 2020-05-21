package com.parkinglotsystem;

public class ParkingLotSystem {
    private Object vehicle;

    public void parkVehicle(Object vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isVehicleParked(Object vehicle) {
        if(this.vehicle == vehicle)
            return true;
        return false;
    }
}
