/**********************************************************************************
 * @Purpose: To Store Properties Of Vehicle(Color, Model, NumberPlate, Attender)
 * @Author: Kumar Shanbhag
 * @Date: 23/05/2020
 **********************************************************************************/
package com.parkinglotsystem.model;

public class Vehicle {
    public String model;
    public String numberPlate;
    public String attender;
    public String color;

    public Vehicle(String color, String model, String numberPlate, String attender) {
        this.color = color;
        this.model = model;
        this.numberPlate = numberPlate;
        this.attender = attender;
    }

    /**
     * Purpose: To Return Vehicle Color
     * @return Color Of Vehicle
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Purpose: To Return Vehicle Model
     * @return Model Of Vehicle
     */
    public String getModel() {
        return this.model;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "color='" + color + '\'' +
                ", model='" + model + '\'' +
                ", numberPlate='" + numberPlate + '\'' +
                ", attender='" + attender + '\'' +
                '}';
    }
}
