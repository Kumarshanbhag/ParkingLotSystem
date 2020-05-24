/*****************************************************************
 * @Purpose: To Park, Unpark And Inform Parking Full To Observers
 * @Author: Kumar Shanbhag
 * @Date: 22/05/2020
 ****************************************************************/

package com.parkinglotsystem;

import com.parkinglotsystem.exception.ParkingLotSystemException;
import com.parkinglotsystem.observer.InformObserver;
import com.parkinglotsystem.observer.ParkingLotSubscriber;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {
    private int parkingCapacity;
    private List vehicle;
    InformObserver informObserver;

    public ParkingLotSystem(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
        this.vehicle = new ArrayList();
        this.informObserver = new InformObserver();
    }

    /**
     * Purpose:To Set The Parking Capacity Of The Parking Lot
     *
     * @param parkingCapacity Is The New Parking Capacity Of Parking Lot
     */
    public void setCapacity(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
    }

    /*
     * Purpose: To Park Vehicle in ParkingLot And Inform Parking Full
     * @param vehicle To Park in ParkingLot
     */
    public void parkVehicle(Object vehicle) {
        if (this.parkingCapacity == this.vehicle.size()) {
            throw new ParkingLotSystemException("Parking Is Full", ParkingLotSystemException.ExceptionType.PARKING_FULL);
        }
        if (this.vehicle.contains(vehicle)) {
            throw new ParkingLotSystemException("Vehicle Already Parked", ParkingLotSystemException.ExceptionType.VEHICLE_ALREADY_PARKED);
        }
        this.vehicle.add(vehicle);
        if (this.parkingCapacity == this.vehicle.size()) {
            informObserver.parkingFull();
        }
    }

    /**
     * Purpose: To Check If Vehicle Parked
     *
     * @param vehicle is Parked Or Not
     * @return true if Vehivle Parked Or Throw Exception
     */
    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicle.contains(vehicle))
            return true;
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    /**
     * Purpose: To Unpark Parked Vehicle
     *
     * @param vehicle To Unpark From ParkingLot
     * @return true if Vehicle Unparked Or Throw Exception
     */
    public boolean unparkVehicle(Object vehicle) {
        if (this.vehicle.contains(vehicle)) {
            this.vehicle.remove(vehicle);
            informObserver.parkingAvailable();
            return true;
        }
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    /**
     * Purpose: Add Subscriber
     *
     * @param subscriber
     */
    public void subscribe(ParkingLotSubscriber subscriber) {
        informObserver.subscribeParkingLotObserver(subscriber);
    }

    /**
     * Purpose: Remove Subscriber
     *
     * @param subscriber
     */
    public void unsubscribe(ParkingLotSubscriber subscriber) {
        informObserver.unsubscribeParkingLotObserver(subscriber);
    }
}
