/*****************************************************************
 * @Purpose: To
 * @Author: Kumar Shanbhag
 * @Date: 22/05/2020
 ****************************************************************/
package com.parkinglotsystem.observer;

import java.util.ArrayList;
import java.util.List;

public class InformObserver {
    public List<ParkingLotSubscriber> observersList;
    private boolean parkingCapacity;

    public InformObserver() {
        this.observersList = new ArrayList<>();
    }

    /**+
     * Purpose : Add subscribers To The List
     * @param parkingLotSubscriber
     */
    public void subscribeParkingLotObserver(ParkingLotSubscriber parkingLotSubscriber) {
        this.observersList.add(parkingLotSubscriber);
    }

    /**
     * Purpose: Remove Subscriber From The List
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
     * Purpose: To Inform Subscribers When Parking Is Full
     */
    public void isParkingFull() {
        for(ParkingLotSubscriber parkingLotSubscriber : observersList)
            parkingLotSubscriber.parkingFull(this.parkingCapacity);
    }

}
