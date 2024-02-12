package kelas;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import gui.Detail;
import gui.List;

/**
 *
 * @author Amalia
 */
public class Suzuki implements Car{
    private String jenis;

    public Suzuki() {
        jenis = mobil[1];
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
