/*****************************************************************
 * @Purpose: To Store Properties Of ParkingSLot(Vehicle,Time,Slot)
 * @Author: Kumar Shanbhag
 * @Date: 23/05/2020
 ****************************************************************/

package com.parkinglotsystem.model;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ParkingSlot {
    public int time;
    public Object vehicle;
    private Integer slot;

    public ParkingSlot(Object vehicle) {
        this.vehicle = vehicle;
        this.time = (int) TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
    }

    /**
     * Purpose: To Set Slot For Parking Vehicle
     * @param slot
     */
    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return Objects.equals(vehicle, that.vehicle);
    }
}
