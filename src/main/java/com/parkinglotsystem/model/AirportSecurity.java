/*****************************************************************
 * @Purpose: To Inform Parking Full To Airport Security
 * @Author: Kumar Shanbhag
 * @Date: 22/05/2020
 ****************************************************************/
package com.parkinglotsystem.model;

import com.parkinglotsystem.observer.ParkingLotSubscriber;

public class AirportSecurity implements ParkingLotSubscriber {

    private boolean parkingCapacity;

    /**
     * Purpose: To change Parking Capacity To True If Parking Is Full
     */
    @Override
    public void parkingFull(boolean parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
    }

    /**
     * Purpose: To Return Parking Capacity
     * @return true if parkingFull Or False
     */
    public boolean isParkingFull() {
        return this.parkingCapacity;
    }
}
