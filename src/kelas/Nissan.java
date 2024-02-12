/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelas;

import gui.Detail;
import gui.List;
import static kelas.Car.mobil;

/**
 *
 * @author Amalia
 */
public class Nissan implements Car{
    private String jenis;

    public Nissan() {
        jenis = mobil[5];
    }

    @Override
    public void listCar(List list) {
        Databases databases = new Databases();
        databases.modelCar(this, list);
    }
    
    @Override
    public  void detailBooking(Detail detail) {
        Databases databases = new Databases();
        databases.createHistory(this, detail);
    }
    
    @Override
    public String getSelectedCarType() {
        return jenis;
    }
}
