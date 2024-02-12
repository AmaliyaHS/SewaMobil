/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelas;

import gui.Detail;

/**
 *
 * @author Amalia
 */
public class Datauser {
    private Car car;
    
    public Datauser(Car car) {
        this.car = car;
    }
    
    public void bookingCar(Detail detail) {
        this.car.detailBooking(detail);
    }
}
