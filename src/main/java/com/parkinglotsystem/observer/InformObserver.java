/**********************************************************************************************
 * @Purpose: To inform About Parking Full, Available And Vehicle Parking Time To Subscriber
 * @Author: Kumar Shanbhag
 * @Date: 22/05/2020
 **********************************************************************************************/
package com.parkinglotsystem.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InformObserver {
    private List<ParkingLotSubscriber> observersList;
    private boolean parkingCapacity;
    private int time;

    public InformObserver() {
        this.observersList = new ArrayList<>();
    }

    /**
     * +
     * Purpose : Add subscribers To The List
     *
     * @param parkingLotSubscriber
     */
    public void subscribeParkingLotObserver(ParkingLotSubscriber parkingLotSubscriber) {
        this.observersList.add(parkingLotSubscriber);
    }

    /**
     * Purpose: Remove Subscriber From The List
     *
     * @param parkingLotSubscriber
     */
    public void unsubscribeParkingLotObserver(ParkingLotSubscriber parkingLotSubscriber) {
        this.observersList.remove(parkingLotSubscriber);
    }

    /**
     * Purpose: To Set Parking Capacity To true If Parking Full
     */
    public void parkingFull() {
        this.parkingCapacity = true;
        isParkingFull();
    }

    /**
     * Purpose: To Set Parking Capacity To False If Parking Full
     */
    public void parkingAvailable() {
        this.parkingCapacity = false;
        isParkingFull();
    }

    /**
     * Purpose: To Inform Subscribers When Parking Is Full
     */
    public void isParkingFull() {
        for (ParkingLotSubscriber parkingLotSubscriber : observersList)
            parkingLotSubscriber.parkingFull(this.parkingCapacity);
    }

    /**
     * Purpose: To Inform Subscribers About Parking Time
     * @param parkingTime To Inform Subscribers
     */
    public void setParkingTime(int parkingTime) {
        this.time = (int) TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()) - parkingTime;
        for (ParkingLotSubscriber parkingLotSubscriber : observersList)
            parkingLotSubscriber.parkingTime(time);
    }
}
