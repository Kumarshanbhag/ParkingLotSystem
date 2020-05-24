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
import java.util.stream.IntStream;

public class ParkingLotSystem {
    private int parkingCapacity;
    private List vehiclesList;
    InformObserver informObserver;

    public ParkingLotSystem(int parkingCapacity) {
        this.informObserver = new InformObserver();
        setCapacity(parkingCapacity);
    }

    /**
     * Purpose:To Set The Parking Capacity Of The Parking Lot
     * @param parkingCapacity Is The New Parking Capacity Of Parking Lot
     * @return Size Of VehicleList
     */
    public int setCapacity(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
        initializeParkingLot();
        return vehiclesList.size();
    }

    /**
     * Purpose: Initialize The VehiclesList With Null Values
     */
    public void initializeParkingLot() {
        this.vehiclesList = new ArrayList();
        IntStream.range(0, this.parkingCapacity)
                .forEach(slots -> vehiclesList.add(null));
    }

    /**
     * Purpose: To Park Vehicle in ParkingLot And Inform Parking Full
     * @param vehicle To Park in ParkingLot
     * @param slot To Park At The Given Slot
     */
    public void parkVehicle(Object vehicle, Integer slot) {
        if (this.parkingCapacity == this.vehiclesList.size() && !vehiclesList.contains(null)) {
            throw new ParkingLotSystemException("Parking Is Full", ParkingLotSystemException.ExceptionType.PARKING_FULL);
        }
        if (this.vehiclesList.contains(vehicle)) {
            throw new ParkingLotSystemException("Vehicle Already Parked", ParkingLotSystemException.ExceptionType.VEHICLE_ALREADY_PARKED);
        }
        this.vehiclesList.set(slot, vehicle);
        if (this.parkingCapacity == this.vehiclesList.size() && !vehiclesList.contains(null)) {
            informObserver.parkingFull();
        }
    }

    /**
     * Purpose: To Check If Vehicle Parked
     * @param vehicle is Parked Or Not
     * @return true if Vehivle Parked Or Throw Exception
     */
    public boolean isVehicleParked(Object vehicle) {
        if (this.vehiclesList.contains(vehicle))
            return true;
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    /**
     * Purpose: To Unpark Parked Vehicle
     * @param vehicle To Unpark From ParkingLot
     * @return true if Vehicle Unparked Or Throw Exception
     */
    public boolean unparkVehicle(Object vehicle) {
        if (this.vehiclesList.contains(vehicle)) {
            this.vehiclesList.remove(vehicle);
            informObserver.parkingAvailable();
            return true;
        }
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    /**
     *Purpose: To Get List Of Empty Parking Slots
     * @return List Of Slots
     */
    public List<Integer> getListOfEmptyParkingSlots() {
        List<Integer> emptyParkingSlotList = new ArrayList<>();
        IntStream.range(0, this.parkingCapacity)
                .filter(slot -> vehiclesList.get(slot) == null)
                .forEach(emptyParkingSlotList::add);
        return emptyParkingSlotList;
    }

    /**
     * Purpose: Add Subscriber
     * @param subscriber
     */
    public void subscribe(ParkingLotSubscriber subscriber) {
        informObserver.subscribeParkingLotObserver(subscriber);
    }

    /**
     * Purpose: Remove Subscriber
     * @param subscriber
     */
    public void unsubscribe(ParkingLotSubscriber subscriber) {
        informObserver.unsubscribeParkingLotObserver(subscriber);
    }
}
