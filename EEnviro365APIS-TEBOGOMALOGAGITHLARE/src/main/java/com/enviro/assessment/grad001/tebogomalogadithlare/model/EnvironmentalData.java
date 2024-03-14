package com.enviro.assessment.grad001.tebogomalogadithlare.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EnvironmentalData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String data; 

    // Constructors
    public EnvironmentalData() {
    }

    public EnvironmentalData(String data) {
        this.data = data;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // You can also add constructors and other methods as needed
}