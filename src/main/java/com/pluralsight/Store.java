package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class Store {

    public static void main(String[] args) {
        // Initialize variables
        ArrayList<Product> inventory = new ArrayList<Product>();
        ArrayList<Product> cart = new ArrayList<Product>();
        double totalAmount = 0.0;


        // Load inventory from CSV file
        loadInventory("products.csv", inventory);

        // Create scanner to read user input
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Display menu and get user choice until they choose to exit
        while (choice != 3) {
            System.out.println("Welcome to the Online com.pluralsight.Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart, scanner, totalAmount);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    // This method should read a CSV file with product information and
    // populate the inventory ArrayList with com.pluralsight.Product objects. Each line
    // of the CSV file contains product information in the following format:
    //
    // id,name,price
    //
    // where id is a unique string identifier, name is the product name,
    // price is a double value representing the price of the product
    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("products.csv"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] str = line.split("\\|");
                    inventory.add(new Product(
                            str[0], // SKU
                            str[1], // Product Name
                            Double.parseDouble(str[2]), // Price
                            str[3]) // Department
                    );
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    // This method should display a list of products from the inventory,
    // and prompt the user to add items to their cart. The method should
    // prompt the user to enter the ID of the product they want to add to
    // their cart. The method should
    // add the selected product to the cart ArrayList.
    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        System.out.println("\nSKU|Product Name|Price|Department");
        for (Product product : inventory) {
            System.out.println(product);
        }

        System.out.println("\nDo you want to add an item to your cart? (N to exit)");

        String choice = "";

        boolean isRunning = true;

        while (isRunning) {
            System.out.print("Enter the SKU: ");
            choice = scanner.nextLine();

            switch (choice.toUpperCase()) {
                case "N":
                    System.out.println("\nReturning to main menu . . .\n");
                    isRunning = false;
                    break;
                default:
                    Product item = findProductById(choice, inventory);
                    if (!(item == null)) {
                        cart.add(item);
                        System.out.println(item.getName() + " successfully added! Returning to main menu.");
                    } else {
                        System.out.println("Error, SKU " + choice + " not found. Returning to main menu.");
                    }
            }
        }
    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {
        // This method should display the items in the cart ArrayList, along
        // with the total cost of all items in the cart. The method should
        // prompt the user to remove items from their cart by entering the ID
        // of the product they want to remove. The method should update the cart ArrayList and totalAmount
        // variable accordingly.
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount) {
        // This method should calculate the total cost of all items in the cart,
        // and display a summary of the purchase to the user. The method should
        // prompt the user to confirm the purchase, and deduct the total cost
        // from their account if they confirm.
    }

    // This method should search the inventory ArrayList for a product with
    // the specified ID, and return the corresponding com.pluralsight.Product object. If
    // no product with the specified ID is found, the method should return
    // null.
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getSku().equals(id)) {
                return inventory.get(i);
            }
        }
        return null;
    }
}