package com.parkinglotsystem.test;

import com.parkinglotsystem.ParkingLot;
import com.parkinglotsystem.model.AirportSecurity;
import com.parkinglotsystem.ParkingLotSystem;
import com.parkinglotsystem.exception.ParkingLotSystemException;
import com.parkinglotsystem.model.ParkingOwner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystemTest {
    private Object vehicle;
    private ParkingLotSystem parkingLotSystem;
    private ParkingLot parkingLot;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem();
        vehicle = new Object();
        parkingLot = new ParkingLot(1);
        parkingLotSystem.addLot(parkingLot);
    }

    //UC1
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
            boolean isParked = parkingLotSystem.isVehicleParked(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    @Test
    public void givenVehicleToPark_WhenCapacityIs2_ShouldBeAbleToPark2Vehicle() {
        parkingLot.setCapacity(2);
        Object vehicle1 = new Object();
        parkingLotSystem.parkVehicle(vehicle);
        boolean isParked1 = parkingLotSystem.isVehicleParked(vehicle);
        parkingLotSystem.parkVehicle(vehicle1);
        boolean isParked2 = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertTrue(isParked1 && isParked2);
    }

    @Test
    public void givenVehicleToPark_WhenSameVehicleAlreadyParked_ShouldThrowException() {
        parkingLot.setCapacity(2);
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Already Parked", e.getMessage());
        }
    }

    //UC2
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

    //UC3
    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsObserver_ShouldInformOwner() {
        ParkingOwner parkingOwner = new ParkingOwner();
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.parkVehicle(new Object());
            Assert.assertTrue(parkingOwner.isParkingFull());
            parkingLotSystem.parkVehicle(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsNotObserver_ShouldInformOwner() {
        ParkingOwner parkingOwner = new ParkingOwner();
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.unsubscribe(parkingOwner);
            parkingLotSystem.parkVehicle(new Object());
            Assert.assertFalse(parkingOwner.isParkingFull());
            parkingLotSystem.parkVehicle(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
    }

    //UC4
    @Test
    public void givenVehicle_WhenParkingFullAndAirportSecurityIsObserver_ShouldInformAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        try {
            parkingLotSystem.subscribe(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle);
            Assert.assertTrue(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParkingFullAndAirportSecurityIsNotObserver_ShouldInformAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        try {
            parkingLotSystem.subscribe(airportSecurity);
            parkingLotSystem.unsubscribe(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle);
            Assert.assertFalse(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
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
            Assert.assertTrue(parkingOwner.isParkingFull());
            Assert.assertTrue(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
    }

    //UC5
    @Test
    public void givenVehicle_WhenParkingAvailableAndOwnerIsObserver_ShouldInformOwner() {
        ParkingOwner parkingOwner = new ParkingOwner();
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.parkVehicle(vehicle);
            Assert.assertTrue(parkingOwner.isParkingFull());
            parkingLotSystem.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
        parkingLotSystem.unparkVehicle(vehicle);
        Assert.assertFalse(parkingOwner.isParkingFull());
    }

    @Test
    public void givenVehicle_WhenParkingavailableAndAirportSecurityIsObserver_ShouldInformAisportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        try {
            parkingLotSystem.subscribe(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle);
            Assert.assertTrue(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
        parkingLotSystem.unparkVehicle(vehicle);
        Assert.assertFalse(airportSecurity.isParkingFull());
    }

    @Test
    public void givenVehicle_WhenParkingAvailableAndParkingOwnerAndAirportSecurityAreObserver_ShouldInformBothParkingOwnerAndAirportSecurity() {
        ParkingOwner parkingOwner = new ParkingOwner();
        AirportSecurity airportSecurity = new AirportSecurity();
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.subscribe(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle);
            Assert.assertTrue(parkingOwner.isParkingFull());
            Assert.assertTrue(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
        parkingLotSystem.unparkVehicle(vehicle);
        Assert.assertFalse(parkingOwner.isParkingFull());
        Assert.assertFalse(airportSecurity.isParkingFull());
    }

    //UC6
    @Test
    public void givenParkingLotSystem_WhenParkingCapacityIsSet_ShouldReturnParkingCapacity() {
        int parkingLotCapacity = parkingLot.setCapacity(100);
        Assert.assertEquals(100, parkingLotCapacity);
    }

    @Test
    public void givenParkingLotSystem_WhenListOfEmptySlotsCalled_ShouldReturnAvailableSlots() {
        List<Integer> expectedList = new ArrayList();
        expectedList.add(1);
        parkingLot.setCapacity(2);
        List<Integer> listOfEmptyParkingSlots = parkingLot.getListOfEmptyParkingSlots();
        parkingLotSystem.parkVehicle(vehicle);
        listOfEmptyParkingSlots = parkingLot.getListOfEmptyParkingSlots();
        Assert.assertEquals(expectedList, listOfEmptyParkingSlots);
    }

    //UC7
    @Test
    public void givenParkingLotSystem_WhenVehicleFound_ShouldReturnVehicleSlot() {
        parkingLot.setCapacity(10);
        parkingLotSystem.parkVehicle(vehicle);
        int slotNumber = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(0, slotNumber);
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleNotFound_ShouldReturnException() {
        parkingLot.setCapacity(10);
        try {
            parkingLotSystem.findVehicle(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    //UC8
    @Test
    public void givenParkingLot_WhenVehicleParkedAndOwnerIsObserver_ShouldInformParkingTimeToOwner() {
        ParkingOwner parkingOwner = new ParkingOwner();
        parkingLotSystem.subscribe(parkingOwner);
        parkingLot.setCapacity(10);
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.getVehicleParkingTime(vehicle);
        int parkingTime = parkingOwner.getParkingTime();
        Assert.assertEquals(0, parkingTime);
    }

    @Test
    public void givenParkingLot_WhenVehicleNotParkedAndOwnerIsOberver_ShouldReturnException() {
        ParkingOwner parkingOwner = new ParkingOwner();
        parkingLotSystem.subscribe(parkingOwner);
        parkingLot.setCapacity(10);
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.getVehicleParkingTime(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleParkedAndAirportSecurityIsObserver_ShouldInformParkingTimeToAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.subscribe(airportSecurity);
        parkingLot.setCapacity(10);
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.getVehicleParkingTime(vehicle);
        int parkingTime = airportSecurity.getParkingTime();
        Assert.assertEquals(0, parkingTime);
    }

    @Test
    public void givenParkingLot_WhenVehicleNotParkedAndAirportSecurityIsObserver_ShouldReturnException() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.subscribe(airportSecurity);
        parkingLot.setCapacity(10);
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.getVehicleParkingTime(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleParkedAndOwnerAndAirportSecurityIsObserver_ShouldInformParkingTimeToBothOwnerAndAirportSecurity() {
        ParkingOwner parkingOwner = new ParkingOwner();
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.subscribe(airportSecurity);
        parkingLot.setCapacity(10);
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.getVehicleParkingTime(vehicle);
        int parkingTime1 = parkingOwner.getParkingTime();
        int parkingTime2 = airportSecurity.getParkingTime();
        Assert.assertEquals(0, parkingTime1);
        Assert.assertEquals(0, parkingTime2);
    }

    @Test
    public void givenParkingLot_WhenVehicleNotParkedAndOwnerAndAirportSecurityIsObserver_ShouldReturnException() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.subscribe(airportSecurity);
        parkingLot.setCapacity(10);
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.getVehicleParkingTime(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    //UC9
    @Test
    public void givenParkingLotSystem_WhenAddedLots_ShouldReturnTrue() {
        boolean isLotAdded = parkingLotSystem.isLotAdded(parkingLot);
        Assert.assertTrue(isLotAdded);
    }

    @Test
    public void givenParkingLotSystem_WhenNotAddedLots_ShouldReturnFalse() {
        ParkingLot parkingLot1 = new ParkingLot(2);
        boolean isLotAdded = parkingLotSystem.isLotAdded(parkingLot1);
        Assert.assertFalse(isLotAdded);
    }

    @Test
    public void givenMultipleCarsLessThanActualCapacity_WhenParkEvenly_shouldReturnLastIndexEmpty() {
        parkingLot.setCapacity(5);
        parkingLotSystem.parkVehicle(vehicle);
        parkingLotSystem.parkVehicle(new Object());
        parkingLotSystem.parkVehicle(new Object());
        parkingLotSystem.parkVehicle(new Object());
        parkingLotSystem.unparkVehicle(vehicle);
        parkingLotSystem.parkVehicle(new Object());
        Object lastEmptySlot = parkingLot.getListOfEmptyParkingSlots().get(0);
        Assert.assertEquals(4, lastEmptySlot);
    }

    @Test
    public void givenMultipleCarsAtMultipleParkingLotsLessThanActualCapacity_WhenParkEvenly_shouldReturnLastIndexEmpty() {
        ParkingLot parkingLot2 = new ParkingLot(3);
        parkingLotSystem.addLot(parkingLot2);
        parkingLot.setCapacity(3);
        parkingLotSystem.parkVehicle(vehicle);          //Lot1Spot1
        parkingLotSystem.parkVehicle(new Object());     //Lot2Spot1
        parkingLotSystem.parkVehicle(new Object());     //Lot1Spot2
        parkingLotSystem.parkVehicle(new Object());     //Lot2Spot2
        parkingLotSystem.unparkVehicle(vehicle);        //Remove Lot1Spot1
        parkingLotSystem.parkVehicle(new Object());     ////Lot1Spot1
        Object lastEmptySlot = parkingLot.getListOfEmptyParkingSlots().get(0);
        Object lastEmptySlot2 = parkingLot2.getListOfEmptyParkingSlots().get(0);
        Assert.assertEquals(2, lastEmptySlot);
        Assert.assertEquals(2, lastEmptySlot2);
    }
}