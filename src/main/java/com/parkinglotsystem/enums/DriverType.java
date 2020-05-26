/*****************************************************************
 * @Purpose: To Park Vehicles Based On Different Types Of Driver
 * @Author: Kumar Shanbhag
 * @Date: 22/05/2020
 ****************************************************************/
package com.parkinglotsystem.enums;

import java.util.Comparator;

public enum DriverType {
    NORMAL(Comparator.reverseOrder()), HANDICAP(Comparator.naturalOrder());

    public Comparator<Integer> order;

    /**
     * Purpose: To Initialize Sorting Order Based On Different Types Of Driver
     * @param order Natural Or Reverse
     */
    DriverType(Comparator<Integer> order) {
        this.order = order;
    }
}
