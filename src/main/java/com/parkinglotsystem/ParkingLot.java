/*****************************************************************
 * @Purpose: To Park And Unpark And Vehicles On Different Slots
 * @Author: Kumar Shanbhag
 * @Date: 25/05/2020
 ****************************************************************/
package com.parkinglotsystem;

import com.parkinglotsystem.enums.DriverType;
import com.parkinglotsystem.enums.VehicleSize;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import com.parkinglotsystem.model.ParkingSlot;
import com.parkinglotsystem.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {
    private int parkingCapacity;
    private List<ParkingSlot> vehiclesList;
    private ParkingSlot parkingSlot;
    private int emptyParkingSlot;

    public ParkingLot(int parkingCapacity) {
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
     * Purpose: To Get List Of Empty Parking Slots
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
     * Purpose: To Park Vehicle in ParkingLot And Inform Parking Full
     * @param vehicle To Park in ParkingLot
     * @param driverType
     * @param vehicleSize
     */
    public void parkVehicle(Vehicle vehicle, DriverType driverType, VehicleSize vehicleSize) {
        if (isVehicleParked(vehicle)) {
            throw new ParkingLotSystemException("Vehicle Already Parked", ParkingLotSystemException.ExceptionType.VEHICLE_ALREADY_PARKED);
        }
        emptyParkingSlot = getEmptyParkingSlotListBasedOnDriverType(driverType);
        parkingSlot = new ParkingSlot(vehicle, emptyParkingSlot, vehicleSize, driverType);
        this.vehiclesList.set(emptyParkingSlot, parkingSlot);
    }

    /**
     * Purpose: To Get Slot Based On Driver Type
     * @param driverType Normal Or Handicap
     * @return slot To Park Vehicle
     */
    private Integer getEmptyParkingSlotListBasedOnDriverType(DriverType driverType) {
        List<Integer> emptySlots = getListOfEmptyParkingSlots().stream()
                .sorted(driverType.order)
                .collect(Collectors.toList());
        return emptySlots.get(0);
    }

    /**
     * Purpose: To Check If Vehicle Parked
     * @param vehicle is Parked Or Not
     * @return true if Vehicle Parked Or Return False
     */
    public boolean isVehicleParked(Vehicle vehicle) {
        VehicleSize vehicleSize = null;
        DriverType driverType = null;
        parkingSlot = new ParkingSlot(vehicle, emptyParkingSlot, vehicleSize, driverType);
        if (vehiclesList.contains(parkingSlot)) {
            return true;
        }
        return false;
    }

    /**
     * Purpose: To Unpark Parked Vehicle
     * @param vehicle To Unpark From ParkingLot
     * @return true
     */
    public boolean unparkVehicle(Object vehicle) {
        int slot = findVehicle(vehicle);
        vehiclesList.set(slot, null);
        return true;
    }

    /**
     * Purpose: To Find Slot On Which vehicle Is Parked
     * @param vehicle To Find In Parking Lot
     * @return slot On Which Vehicle Is Parked
     */
    public int findVehicle(Object vehicle) {
        return vehiclesList.indexOf(parkingSlot);
    }

    /**
     * Purpose: To Find At what Time Vehicle Was Parked
     * @param vehicle Whose Time To Return
     * @return time
     */
    public int getVehicleParkingTime(Object vehicle) {
        return parkingSlot.time;
    }

    /**
     * Purpose: To check If Parking Is Full
     * @return true if Parking Full or False
     */
    public boolean isParkingFull() {
        if (this.parkingCapacity == this.vehiclesList.size() && !vehiclesList.contains(null)) {
            return true;
        }
        return false;
    }

    /**
     * Purpose: To Find Vehicle Based On Vehicle Color
     * @param color Of Vehicle To Find
     * @return List Of Vehicle With Same Color
     */

    public List<Integer> findByColor(String color) {
        List<Integer> vehicleListByColor = this.vehiclesList.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .filter(parkingSlot -> parkingSlot.getVehicle().getColor().equalsIgnoreCase(color))
                .map(parkingSlot -> parkingSlot.getLocation())
                .collect(Collectors.toList());
        return vehicleListByColor;
    }

    /**
     * Purpose: To Find Vehicle Based On Vehicle Color And Model
     * @param color Of Vehicle To Find
     * @param model Of Vehicle To Find
     * @return List Of Vehicle With Same Color And Model
     */
    public List<String> findByColorAndModel(String color, String model) {
        List<String> vehicleListByColorAndModel = this.vehiclesList.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .filter(parkingSlot -> parkingSlot.getVehicle().getColor().equalsIgnoreCase(color))
                .filter(parkingSlot -> parkingSlot.getVehicle().getModel().equalsIgnoreCase(model))
                .map(parkingSlot -> parkingSlot.getLocation() + " " + parkingSlot.getVehicle())
                .collect(Collectors.toList());
        return vehicleListByColorAndModel;
    }

    /**
     * Purpose: To Find Vehicle Based On Vehicle Color And Model
     * @param model Of Vehicle To Find
     * @return List Of Vehicle With Same Model
     */
    public List<String> findByModel(String model) {
        List<String> vehicleListByModel = this.vehiclesList.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .filter(parkingSlot -> parkingSlot.getVehicle().getModel().equalsIgnoreCase(model))
                .map(parkingSlot -> parkingSlot.getLocation() + " " + parkingSlot.getVehicle())
                .collect(Collectors.toList());
        return vehicleListByModel;
    }

    /**
     * Purpose: To Find Vehicle Based On Vehicle Parked Time
     * @param parkedTime of Vehicle Parked
     * @return List Of Vehicle Parked Within Given Time
     */
    public List<String> findByTime(int parkedTime) {
        List<String> vehicleListByTime = this.vehiclesList.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .filter(parkingSlot -> (parkingSlot.time - (int) TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())) < parkedTime)
                .map(parkingSlot -> parkingSlot.getLocation() + " " + parkingSlot.getVehicle())
                .collect(Collectors.toList());
        return vehicleListByTime;
    }

    /**
     * Purpose: To Find Vehicle Based On Vehicle Size, DriverType And Slot at Which It Is Parked
     * @param vehicleSize To Match Size(Small, Large)
     * @param driverType To Match DriverType(Normal, Handicap)
     * @param slot To Check Vehicle Is At Present At Desired Slot
     * @return List Of Vehicle With Same VehiceSize, DriverType And Slot
     */
    public List<String> findBySizeDriverAndSlot(VehicleSize vehicleSize, DriverType driverType, int slot) {
        List<String> vehicleListByTime = this.vehiclesList.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .filter(parkingSlot -> parkingSlot.vehicleSize.equals(vehicleSize))
                .filter(parkingSlot -> parkingSlot.driverType.equals(driverType))
                .filter(parkingSlot -> parkingSlot.getLocation() == slot )
                .map(parkingSlot -> parkingSlot.getLocation() + " " + parkingSlot.getVehicle())
                .collect(Collectors.toList());
        return vehicleListByTime;
    }
}
