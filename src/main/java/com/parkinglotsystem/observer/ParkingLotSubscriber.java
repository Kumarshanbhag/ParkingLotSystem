package com.parkinglotsystem.observer;

public interface ParkingLotSubscriber {
    public boolean isParkingFull();

    void parkingFull(boolean parkingCapacity);
}
