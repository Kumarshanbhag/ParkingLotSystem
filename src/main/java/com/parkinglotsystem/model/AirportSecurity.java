/*****************************************************************
 * @Purpose: To Inform Parking Full To Airport Security
 * @Author: Kumar Shanbhag
 * @Date: 22/05/2020
 ****************************************************************/
package com.parkinglotsystem.model;

import com.parkinglotsystem.ParkingLotSystemObserver;

public class AirportSecurity implements ParkingLotSystemObserver {

    private boolean parkingCapacity;

    /**
     * Purpose: To change Parking Capacity To True If Parking Is Full
     */
    @Override
    public void parkingFull() {
        this.parkingCapacity = true;
    }

    /**
     * Purpose: To Return Parking Capacity
     * @return true if parkingFull Or False
     */
    @Override
    public boolean isParkingFull() {
        return this.parkingCapacity;
    }
}
