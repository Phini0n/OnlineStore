package com.pluralsight;

public class Product {
    private String sku;
    private String name;
    private double price; // Set at construction, does not need setter or getter.
    private String department;

    public Product(String sku, String name, double price, String department) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Product{" +
                "sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", department='" + department + '\'' +
                '}';
    }
}
