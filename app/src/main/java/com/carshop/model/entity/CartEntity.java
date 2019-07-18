package com.carshop.model.entity;

public class CartEntity {
    private int id;
    private int idCarService;
    private String name;
    private int amountTotal;
    private int amountAdd;
    private double price;

    public CartEntity() {
    }

    public CartEntity(int id, int idCarService, String name, int amountTotal, int amountAdd, double price) {
        this.id = id;
        this.idCarService = idCarService;
        this.name = name;
        this.amountTotal = amountTotal;
        this.amountAdd = amountAdd;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getIdCarService() {
        return idCarService;
    }

    public String getName() {
        return name;
    }

    public int getAmountTotal() {
        return amountTotal;
    }

    public int getAmountAdd() {
        return amountAdd;
    }

    public double getPrice() {
        return price;
    }
}
