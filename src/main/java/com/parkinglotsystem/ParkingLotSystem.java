package com.parkinglotsystem;

import com.parkinglotsystem.exception.ParkingLotSystemException;

public class ParkingLotSystem {
    private Object vehicle;

    public void parkVehicle(Object vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isVehicleParked(Object vehicle) {
        if(this.vehicle == vehicle)
            return true;
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }
}
