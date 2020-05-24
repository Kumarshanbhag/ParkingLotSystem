/*****************************************************************
 * @Purpose: To Inform Parking Full To Airport Security
 * @Author: Kumar Shanbhag
 * @Date: 22/05/2020
 ****************************************************************/
package com.parkinglotsystem.model;

import com.parkinglotsystem.observer.ParkingLotSubscriber;

public class AirportSecurity implements ParkingLotSubscriber {

    private boolean parkingCapacity;
    private int parkingTime;

    /**
     * Purpose: To change Parking Capacity To True If Parking Is Full
     * @param parkingCapacity True Or False
     */
    @Override
    public void parkingFull(boolean parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
    }

    /**
     * Purpose: To Return Parking Capacity
     * @return true if parkingFull Or False
     */
    @Override
    public boolean isParkingFull() {
        return this.parkingCapacity;
    }

    /**
     * Purpose: To set ParkingTime For Vehicle
     * @param parkingTime Time In Minutes
     */
    @Override
    public void parkingTime(int parkingTime) {
        this.parkingTime = parkingTime;
    }

    /**
     * Purpose: To Return Parking Time
     * @return parkingTime
     */
    @Override
    public int getParkingTime() {
        return parkingTime;
    }
}
