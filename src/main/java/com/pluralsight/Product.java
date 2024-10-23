package com.pluralsight;

public class Product {
    private String sku;
    private String name;
    private double price; // Set at construction, does not need setter or getter.
    private final Department department; // Set at construction, does not need setter or getter.
    private enum Department {
        AUDIO_VIDEO, GAMES, ELECTRONICS, CLOTHING, COSMETICS, FURNITURE, GARDENING, HARDWARE, SPORTING_GOODS
    }

    public Product(String sku, String name, double price, String categString) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.department = Department.valueOf(categString);
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDepartment() {
        return department.toString();
    }

    @Override
    public String toString() {
        return "Product{" +
                "sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", department=" + department +
                '}';
    }
}

