package com.parkinglotsystem.observer;

public interface ParkingLotSubscriber {
    boolean isParkingFull();

    void parkingFull(boolean parkingCapacity);
}
