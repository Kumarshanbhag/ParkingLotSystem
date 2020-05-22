/************************************************************************
 * @Purpose: To Create Custom Exception For ParkingLotSystem Exception
 * @Author: Kumar Shanbhag
 * @Date: 22/05/2020
 ************************************************************************/
package com.parkinglotsystem.exception;

public class ParkingLotSystemException extends RuntimeException{
    //To Define specific type  Of Exception
    private ExceptionType type;

    public enum ExceptionType {
        VEHICLE_NOT_FOUND, PARKING_FULL;
    }

    public ParkingLotSystemException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}
