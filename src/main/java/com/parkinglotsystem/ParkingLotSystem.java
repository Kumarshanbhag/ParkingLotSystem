package com.parkinglotsystem;

import com.parkinglotsystem.exception.ParkingLotSystemException;

public class ParkingLotSystem {
    private final int parkingCapacity;
    private Object vehicle;
    private ParkingOwner parkingOwner;
    private int currentCapacity = 0;

    public ParkingLotSystem(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
    }

    public void parkVehicle(Object vehicle) {
        if (this.parkingCapacity == currentCapacity) {
            parkingOwner.parkingFull();
            throw new ParkingLotSystemException("Parking Is Full", ParkingLotSystemException.ExceptionType.PARKING_FULL);
        }
        this.vehicle = vehicle;
        currentCapacity++;
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicle.equals(vehicle))
            return true;
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public boolean unparkVehicle(Object vehicle) {
        if (this.vehicle.equals(vehicle))
            return true;
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public void registerOwner(ParkingOwner parkingOwner) {
        this.parkingOwner = parkingOwner;
    }
}
