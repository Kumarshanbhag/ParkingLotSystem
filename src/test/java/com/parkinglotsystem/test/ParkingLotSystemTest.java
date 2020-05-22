package com.parkinglotsystem.test;

import com.parkinglotsystem.model.AirportSecurity;
import com.parkinglotsystem.ParkingLotSystem;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import com.parkinglotsystem.model.ParkingOwner;
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
    public void givenVehicle_WhenParkingFullAndOwnerIsObserver_ShouldInformOwner() {
        ParkingOwner parkingOwner = new ParkingOwner();
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.parkVehicle(new Object());
            parkingLotSystem.parkVehicle(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
            Assert.assertTrue(parkingOwner.isParkingFull());
        }
    }

    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsNotObserver_ShouldInformOwner() {
        ParkingOwner parkingOwner = new ParkingOwner();
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.unsubscribe(parkingOwner);
            parkingLotSystem.parkVehicle(new Object());
            parkingLotSystem.parkVehicle(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
            Assert.assertFalse(parkingOwner.isParkingFull());
        }
    }

    @Test
    public void givenVehicle_WhenParkingFullAndAirportSecurityIsObserver_ShouldInformAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        try {
            parkingLotSystem.subscribe(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
            Assert.assertTrue(airportSecurity.isParkingFull());
        }
    }

    @Test
    public void givenVehicle_WhenParkingFullAndAirportSecurityIsNotObserver_ShouldInformAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        try {
            parkingLotSystem.subscribe(airportSecurity);
            parkingLotSystem.unsubscribe(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
            Assert.assertFalse(airportSecurity.isParkingFull());
        }
    }

    @Test
    public void givenVehicle_WhenParkingFullAndParkingOwnerAndAirportSecurityAreObserver_ShouldInformBothParkingOwnerAndAirportSecurity() {
        ParkingOwner parkingOwner = new ParkingOwner();
        AirportSecurity airportSecurity = new AirportSecurity();
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.subscribe(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
            Assert.assertTrue(parkingOwner.isParkingFull());
            Assert.assertTrue(airportSecurity.isParkingFull());
        }
    }

    @Test
    public void givenVehicle_WhenParkingavailableAndOwnerIsObserver_ShouldInformOwner() {
        ParkingOwner parkingOwner = new ParkingOwner();
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
            Assert.assertTrue(parkingOwner.isParkingFull());
        }
        parkingLotSystem.unparkVehicle(vehicle);
        Assert.assertFalse(parkingOwner.isParkingFull());
    }
}
