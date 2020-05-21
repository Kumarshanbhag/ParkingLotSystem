package com.parkinglotsystem.test;

import com.parkinglotsystem.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {
    Object vehicle;

    @Before
    public void setUp() throws Exception {
        vehicle = new Object();
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        boolean isParked = parkingLotSystem.parkVehicle(vehicle);
        Assert.assertTrue(isParked);
    }
}
