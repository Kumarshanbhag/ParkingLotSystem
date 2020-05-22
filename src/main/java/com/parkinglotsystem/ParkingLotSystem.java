/*****************************************************************
 * @Purpose: To Park, Unpark And Inform Parking Full To Observers
 * @Author: Kumar Shanbhag
 * @Date: 22/05/2020
 ****************************************************************/

package com.parkinglotsystem;

import com.parkinglotsystem.exception.ParkingLotSystemException;

import java.util.ArrayList;

public class ParkingLotSystem {
    private int parkingCapacity;
    private Object vehicle;
    private int currentCapacity = 0;
    private ArrayList<ParkingLotSystemObserver> parkingLotSystemObservers;

    public ParkingLotSystem(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
        this.parkingLotSystemObservers = new ArrayList<ParkingLotSystemObserver>();
    }

    /**
     * Purpose: To Park Vehicle in ParkingLot And Inform Parking Full
     * @param vehicle To Park in ParkingLot
     */
    public void parkVehicle(Object vehicle) {
        if (this.parkingCapacity == currentCapacity) {
            for(ParkingLotSystemObserver parkingLotSystemObserver : parkingLotSystemObservers)
            parkingLotSystemObserver.parkingFull();
            throw new ParkingLotSystemException("Parking Is Full", ParkingLotSystemException.ExceptionType.PARKING_FULL);
        }
        this.vehicle = vehicle;
        currentCapacity++;
    }

    /**
     * Purpose: To Check If Vehivle Parked
     * @param vehicle is Parked Or Not
     * @return true if Vehivle Parked Or throw Exception
     */
    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicle.equals(vehicle))
            return true;
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    /**
     * Purpose: To Unpark Parked Vehicle
     * @param vehicle To Unpark From ParkingLot
     * @return true if Vehicle Unparked Or Throw Exception
     */
    public boolean unparkVehicle(Object vehicle) {
        if (this.vehicle.equals(vehicle))
            return true;
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    /**
     * Purpose: Add Observer Like Owner and Security In List
     * @param observer To Add in the List
     */
    public void registerParkingLotSystemObserver(ParkingLotSystemObserver observer) {
        this.parkingLotSystemObservers.add(observer);
    }
}
