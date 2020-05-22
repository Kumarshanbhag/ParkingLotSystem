package com.parkinglotsystem.test;

import com.parkinglotsystem.AirportSecurity;
import com.parkinglotsystem.ParkingLotSystem;
import com.parkinglotsystem.ParkingOwner;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {
    private Object vehicle;
    private ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(1);
        vehicle = new Object();
    }

    @Test
    public void givenVehicleToPark_WhenParked_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicleToPark_WhenNotParked_ShouldReturnException() {
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.isVehicleParked(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    @Test
    public void givenVehicleToUnpark_WhenParked_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle);
        boolean unparkVehicle = parkingLotSystem.unparkVehicle(vehicle);
        Assert.assertTrue(unparkVehicle);
    }

    @Test
    public void givenVehicleToUnpark_WhenNotParked_ShouldReturnException() {
        parkingLotSystem.parkVehicle(vehicle);
        try {
            parkingLotSystem.unparkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParkingFull_ShouldInformOwner() {
        ParkingOwner parkingOwner = new ParkingOwner();
        try {
            parkingLotSystem.registerParkingLotSystemObserver(parkingOwner);
            parkingLotSystem.parkVehicle(new Object());
            parkingLotSystem.parkVehicle(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertTrue(parkingOwner.isParkingFull());
        }
    }

    @Test
    public void givenVehicle_WhenParkingFull_ShouldInformToAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        try {
            parkingLotSystem.registerParkingLotSystemObserver(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
            boolean parkingFull = airportSecurity.isParkingFull();
            Assert.assertTrue(parkingFull);
        }
    }
}
