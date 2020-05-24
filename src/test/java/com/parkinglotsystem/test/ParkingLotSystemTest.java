package com.parkinglotsystem.test;

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
    private List<Integer> listOfEmptyParkingSlots;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(1);
        vehicle = new Object();
        listOfEmptyParkingSlots = parkingLotSystem.getListOfEmptyParkingSlots();
    }

    //UC1
    @Test
    public void givenVehicleToPark_WhenParked_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicleToPark_WhenNotParked_ShouldReturnException() {
        parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
        boolean isParked = parkingLotSystem.isVehicleParked(new Object());
        Assert.assertFalse(isParked);
    }

    @Test
    public void givenVehicleToPark_WhenCapacityIs2_ShouldBeAbleToPark2Vehicle() {
        parkingLotSystem.setCapacity(2);
        Object vehicle1 = new Object();
        parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
        boolean isParked1 = parkingLotSystem.isVehicleParked(vehicle);
        parkingLotSystem.parkVehicle(vehicle1, listOfEmptyParkingSlots.get(0));
        boolean isParked2 = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertTrue(isParked1 && isParked2);
    }

    @Test
    public void givenVehicleToPark_WhenSameVehicleAlreadyParked_ShouldThrowException() {
        parkingLotSystem.setCapacity(2);
        try {
            parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
            parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Already Parked", e.getMessage());
        }
    }

    //UC2
    @Test
    public void givenVehicleToUnpark_WhenParked_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
        boolean unparkVehicle = parkingLotSystem.unparkVehicle(vehicle);
        Assert.assertTrue(unparkVehicle);
    }

    @Test
    public void givenVehicleToUnpark_WhenNotParked_ShouldReturnException() {
        parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
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
            parkingLotSystem.parkVehicle(new Object(), listOfEmptyParkingSlots.get(0));
            Assert.assertTrue(parkingOwner.isParkingFull());
            parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
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
            parkingLotSystem.parkVehicle(new Object(), listOfEmptyParkingSlots.get(0));
            Assert.assertFalse(parkingOwner.isParkingFull());
            parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
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
            parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
            Assert.assertTrue(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(new Object(), listOfEmptyParkingSlots.get(0));
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
            parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
            Assert.assertFalse(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(new Object(), listOfEmptyParkingSlots.get(0));
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
            parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
            Assert.assertTrue(parkingOwner.isParkingFull());
            Assert.assertTrue(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(new Object(), listOfEmptyParkingSlots.get(0));
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
            parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
            Assert.assertTrue(parkingOwner.isParkingFull());
            parkingLotSystem.parkVehicle(new Object(), listOfEmptyParkingSlots.get(0));
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
            parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
            Assert.assertTrue(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(new Object(), listOfEmptyParkingSlots.get(0));
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
            parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
            Assert.assertTrue(parkingOwner.isParkingFull());
            Assert.assertTrue(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(new Object(), listOfEmptyParkingSlots.get(0));
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
        int parkingLotCapacity = parkingLotSystem.setCapacity(100);
        Assert.assertEquals(100, parkingLotCapacity);
    }

    @Test
    public void givenParkingLotSystem_WhenListOfEmptySlotsCalled_ShouldReturnAvailableSlots() {
        List<Integer> expectedList = new ArrayList();
        expectedList.add(1);
        parkingLotSystem.setCapacity(2);
        listOfEmptyParkingSlots = parkingLotSystem.getListOfEmptyParkingSlots();
        parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
        listOfEmptyParkingSlots = parkingLotSystem.getListOfEmptyParkingSlots();
        Assert.assertEquals(expectedList, listOfEmptyParkingSlots);
    }

    //UC7
    @Test
    public void givenParkingLotSystem_WhenVehicleFound_ShouldReturnVehicleSlot() {
        parkingLotSystem.setCapacity(10);
        listOfEmptyParkingSlots = parkingLotSystem.getListOfEmptyParkingSlots();
        parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
        int slotNumber = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(0, slotNumber);
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleNotFound_ShouldReturnException() {
        parkingLotSystem.setCapacity(10);
        listOfEmptyParkingSlots = parkingLotSystem.getListOfEmptyParkingSlots();
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
        parkingLotSystem.setCapacity(10);
        listOfEmptyParkingSlots = parkingLotSystem.getListOfEmptyParkingSlots();
        parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
        parkingLotSystem.getVehicleParkingTime(vehicle);
        int parkingTime = parkingOwner.getParkingTime();
        Assert.assertEquals(0, parkingTime);
    }

    @Test
    public void givenParkingLot_WhenVehicleNotParkedAndOwnerIsOberver_ShouldReturnException() {
        ParkingOwner parkingOwner = new ParkingOwner();
        parkingLotSystem.subscribe(parkingOwner);
        parkingLotSystem.setCapacity(10);
        listOfEmptyParkingSlots = parkingLotSystem.getListOfEmptyParkingSlots();
        try {
            parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
            parkingLotSystem.getVehicleParkingTime(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleParkedAndAirportSecurityIsObserver_ShouldInformParkingTimeToAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.subscribe(airportSecurity);
        parkingLotSystem.setCapacity(10);
        listOfEmptyParkingSlots = parkingLotSystem.getListOfEmptyParkingSlots();
        parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
        parkingLotSystem.getVehicleParkingTime(vehicle);
        int parkingTime = airportSecurity.getParkingTime();
        Assert.assertEquals(0, parkingTime);
    }

    @Test
    public void givenParkingLot_WhenVehicleNotParkedAndAirportSecurityIsObserver_ShouldReturnException() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.subscribe(airportSecurity);
        parkingLotSystem.setCapacity(10);
        listOfEmptyParkingSlots = parkingLotSystem.getListOfEmptyParkingSlots();
        try {
            parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
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
        parkingLotSystem.setCapacity(10);
        listOfEmptyParkingSlots = parkingLotSystem.getListOfEmptyParkingSlots();
        parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
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
        parkingLotSystem.setCapacity(10);
        listOfEmptyParkingSlots = parkingLotSystem.getListOfEmptyParkingSlots();
        try {
            parkingLotSystem.parkVehicle(vehicle, listOfEmptyParkingSlots.get(0));
            parkingLotSystem.getVehicleParkingTime(new Object());
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }
}