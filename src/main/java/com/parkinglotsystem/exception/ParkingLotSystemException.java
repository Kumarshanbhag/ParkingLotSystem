package com.parkinglotsystem.exception;

public class ParkingLotSystemException extends RuntimeException{
    public enum ExceptionType {
        VEHICLE_NOT_FOUND;
    }

    private ExceptionType type;

    public ParkingLotSystemException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}
