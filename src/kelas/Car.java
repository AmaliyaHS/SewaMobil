package kelas;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import gui.Detail;
import gui.History;
import gui.List;

/**
 *
 * @author Amalia
 */
public interface Car {
    String[] mobil = {"Toyota", "Suzuki", "Honda", "Daihatsu", "Mitsubishi", "Nissan", "KIA", "Mazda"};
    List list = new List();
    Detail detail = new Detail();
    History history = new History();
    void listCar(List list);
    void detailBooking(Detail detail);
    String getSelectedCarType();
}
