package com.parkinglotsystem.observer;

public interface ParkingLotSubscriber {
    boolean isParkingFull();

    void parkingFull(boolean parkingCapacity);

    void parkingTime(int parkingTime);

    int getParkingTime();
}
