/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Amalia
 * Created: May 24, 2023
 */

CREATE DATABASE dbmobil;
USE dbdemo;

CREATE TABLE fotouser(
    username VARCHAR(30),
    gambar BLOB
);

CREATE TABLE datauser(
    username VARCHAR(30),
    no_hp VARCHAR(15),
    password VARCHAR(20)
);

CREATE TABLE detail(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    gambar BLOB,
    jenis VARCHAR(15),
    model VARCHAR(25),
    ukuran VARCHAR(10),
    harga INT,
    pemilik VARCHAR(30),
    hp_pemilik VARCHAR(15)
);

CREATE TABLE history(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    jenis VARCHAR(15),
    model VARCHAR(25),
    ukuran VARCHAR(10),
    harga INT
);

CREATE TABLE list(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    jenis VARCHAR(15),
    gambar BLOB,
    model VARCHAR(30)
    );