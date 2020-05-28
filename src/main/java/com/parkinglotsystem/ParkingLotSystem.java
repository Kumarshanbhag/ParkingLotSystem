/***************************************************************************
 * @Purpose: To Park, Unpark And Inform Parking Availability To Observers
 * @Author: Kumar Shanbhag
 * @Date: 22/05/2020
 ****************************************************************************/

package com.parkinglotsystem;

import com.parkinglotsystem.enums.DriverType;
import com.parkinglotsystem.enums.VehicleSize;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import com.parkinglotsystem.model.Vehicle;
import com.parkinglotsystem.observer.InformObserver;
import com.parkinglotsystem.observer.ParkingLotSubscriber;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotSystem {

    private List<ParkingLot> parkingLotList;
    private InformObserver informObserver;
    private ParkingLot parkingLot;

    public ParkingLotSystem() {
        this.informObserver = new InformObserver();
        this.parkingLotList = new ArrayList<>();
    }

    /**
     * Purpose: To Add Parking Lot In ParkingLotSystem
     * @param parkingLot
     */
    public void addLot(ParkingLot parkingLot) {
        this.parkingLotList.add(parkingLot);
    }

    /**
     * Purpose: To Check If Lot Added
     * @param parkingLot
     * @return
     */
    public boolean isLotAdded(ParkingLot parkingLot) {
        if(this.parkingLotList.contains(parkingLot)){
            return true;
        }
        return false;
    }

    /**
     * Purpose: To Park Vehicle in ParkingLot And Inform Parking Full
     * @param vehicle To Park in ParkingLot
     * @param driverType
     * @param vehicleSize
     */
    public void parkVehicle(Vehicle vehicle, DriverType driverType, VehicleSize vehicleSize) {
        parkingLot = getParkingLotHavingMaxSpace();
        if (parkingLot.isParkingFull()) {
            throw new ParkingLotSystemException("Parking Is Full", ParkingLotSystemException.ExceptionType.PARKING_FULL);
        }
        parkingLot.parkVehicle(vehicle, driverType, vehicleSize);
        if (parkingLot.isParkingFull()) {
            informObserver.parkingFull();
        }
    }

    /**
     * Purpose: To Check If Vehicle Parked
     * @param vehicle is Parked Or Not
     * @return true if Vehicle Parked Or Throw Exception
     */
    public boolean isVehicleParked(Vehicle vehicle) {
        for (ParkingLot parkingLot : this.parkingLotList) {
            if (parkingLot.isVehicleParked(vehicle))
                return true;
        }
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    /**
     * Purpose: To Unpark Parked Vehicle
     * @param vehicle To Unpark From ParkingLot
     * @return true if Vehicle Unparked Or Throw Exception
     */
    public boolean unparkVehicle(Vehicle vehicle) {
        for (ParkingLot parkingLot : this.parkingLotList) {
            if (parkingLot.isVehicleParked(vehicle)) {
                parkingLot.unparkVehicle(vehicle);
                informObserver.parkingAvailable();
                return true;
            }
        }
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    /**
     * Purpose: To Get List Of Parking Slots With Max Space
     * @return parkingLot
     */
    private ParkingLot getParkingLotHavingMaxSpace() {
        return parkingLotList.stream()
                .sorted(Comparator.comparing(list -> list.getListOfEmptyParkingSlots().size(), Comparator.reverseOrder()))
                .collect(Collectors.toList()).get(0);
    }

    /**
     * Purpose: To Find Slot On Which vehicle Is Parked
     * @param vehicle To Find In Parking Lot
     * @return slot if Vehicle Found Or Throw Exception
     */
    public int findVehicle(Vehicle vehicle) {
        for (ParkingLot parkingLot : parkingLotList)
            if (parkingLot.isVehicleParked(vehicle))
                return parkingLot.findVehicle(vehicle);
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    /**
     * Purpose: To Find At what Time Vehicle Was Parked
     * @param vehicle Whose Time To Return
     * @return time If Vehcile Found Or Throw Exception
     */
    public void getVehicleParkingTime(Vehicle vehicle) {
        for (ParkingLot parkingLot : this.parkingLotList)
            if (parkingLot.isVehicleParked(vehicle)) {
                informObserver.setParkingTime(parkingLot.getVehicleParkingTime(vehicle));
                return;
            }
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
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

    /**
     * Purpose: To Find Vehicle Based On Vehicle Color
     * @param color Of Vehicle To Find
     * @return List Of Vehicle With Same Color
     */
    public List<List<Integer>> findVehicleByColor(String color) {
        List<List<Integer>> vehicleListByColor = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findByColor(color))
                .collect(Collectors.toList());
        return vehicleListByColor;
    }

    /**
     * Purpose: To Find Vehicle Based On Vehicle Color And Model
     * @param color Of Vehicle To Find
     * @param model Of Vehicle To Find
     * @return List Of Vehicle With Same Color And Model
     */
    public List<List<String>> findVehicleByColorAndModel(String color, String model) {
        List<List<String>> vehicleListByColorAndModel = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findByColorAndModel(color, model))
                .collect(Collectors.toList());
        return vehicleListByColorAndModel;
    }

    /**
     * Purpose: To Find Vehicle Based On Vehicle Color And Model
     * @param model Of Vehicle To Find
     * @return List Of Vehicle With Same Model
     */
    public List<List<String>> findVehicleByModel(String model) {
        List<List<String>> vehicleListByColor = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findByModel(model))
                .collect(Collectors.toList());
        return vehicleListByColor;
    }

    /**
     * Purpose: To Find Vehicle Based On Vehicle Parked Time
     * @param parkedTime of Vehicle Parked
     * @return List Of Vehicle Parked Within Given Time
     */
    public List<List<String>> findVehicleByTime(int parkedTime) {
        List<List<String>> vehicleListByColor = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findByTime(parkedTime))
                .collect(Collectors.toList());
        return vehicleListByColor;
    }

    /**
     * Purpose: To Find Vehicle Based On Vehicle Size, DriverType And Slot at Which It Is Parked
     * @param vehicleSize To Match Size(Small, Large)
     * @param driverType To Match DriverType(Normal, Handicap)
     * @param slot To Check Vehicle Is At Present At Desired Slot
     * @return List Of Vehicle With Same VehiceSize, DriverType And Slot
     */
    public List<List<String>> findVehicleBySizeDriverAndSlot(VehicleSize vehicleSize, DriverType driverType, int slot) {
        List<List<String>> vehicleListByColor = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findBySizeDriverAndSlot(vehicleSize, driverType, slot))
                .collect(Collectors.toList());
        return vehicleListByColor;
    }
}
