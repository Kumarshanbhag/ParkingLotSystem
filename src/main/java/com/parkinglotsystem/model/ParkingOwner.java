/*****************************************************************
 * @Purpose: To Inform Parking Full To Parking Owner
 * @Author: Kumar Shanbhag
 * @Date: 22/05/2020
 ****************************************************************/
package com.parkinglotsystem.model;

import com.parkinglotsystem.ParkingLotSystemObserver;

public class ParkingOwner implements ParkingLotSystemObserver {
    private boolean parkingCapacity;

    /**
     * Purpose: To change Parking Capacity To True If Parking Is Full
     */
    public void parkingFull() {
        this.parkingCapacity = true;
    }

    /**
     * Purpose: To Return Parking Capacity
     * @return true if parkingFull Or False
     */
    public boolean isParkingFull() {
        return this.parkingCapacity;
    }
}
