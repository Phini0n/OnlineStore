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
        System.out.println("Welcome to the Online com.pluralsight.Store!\n");
        while (choice != 3) {
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("\nEnter: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner, totalAmount);
                    break;
                case 2:
                    displayCart(cart, scanner, totalAmount);
                    break;
                case 3:
                    System.out.println("\nThank you for shopping with us!");
                    break;
                default:
                    System.out.println("\nInvalid choice!");
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
    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner,
                                       double totalAmount) {
        System.out.println("\nSKU|Product Name|Price|Department");
        for (Product product : inventory) {
            System.out.println(product);
        }

        int choice = -1;

        // Menu
        while (choice != 3) {
            System.out.println("\n1. Filter");
            System.out.println("2. Add a Product to your cart");
            System.out.println("3. Back to Main Menu");
            System.out.print("\nEnter: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1: // Filter submenu
                    int filterChoice = -1;
                    System.out.println("\n1. Name");
                    System.out.println("2. Price");
                    System.out.println("3. Department");
                    System.out.println("4. Back");
                    System.out.print("\nEnter: ");
                    filterChoice = scanner.nextInt();
                    try {
                        switch (filterChoice) {
                            case 1: // Name
                                filterProducts(inventory, scanner, 0);
                                break;
                            case 2: // Price
                                filterProducts(inventory, scanner, 1);
                                break;
                            case 3: // Department
                                filterProducts(inventory, scanner, 2);
                                break;
                            case 4:
                                break;
                            default:
                                System.out.println("Invalid choice!");
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e);
                    }
                    break;
                case 2:
                    promptItems(inventory, cart, scanner, true);
                    break;
                case 3:
                    System.out.println("\nReturning to main menu");
                    break;
                default:
                    System.out.println("\nInvalid choice!");
                    break;
            }
        }
    }


    // Prints out items specified by the filterType
    private static void filterProducts(ArrayList<Product> inventory, Scanner scanner, int filterType) {
        String operation = switch (filterType) {
            case 0 -> "name";
            case 1 -> "price";
            case 2 -> "department";
            default -> "";
        };

        System.out.print("Enter the item's " + operation +  ": ");

        switch (filterType) {
            case 0: // name
                String nameFilter = scanner.next();
                for (Product product : inventory) {
                    if (product.getName().contains(nameFilter))
                        System.out.println(product);
                }
                    break;
            case 1: // price
                double doubleFilter = scanner.nextDouble();
                for (Product product : inventory) {
                    if (product.getPrice() == doubleFilter)
                        System.out.println(product);
                }
                break;
            case 2: // department
                String filterDept = scanner.next();
                for (Product product : inventory) {
                    if (product.getDepartment().contains(filterDept))
                        System.out.println(product);
                }
        }
    }


    // Checks if item exists in sourcedList and if the item exists in sourcedList, it removes it from changedList.
    // Putting the same list as a parameter just removes the item from the list.
    // This condenses the processes done by displayProducts and displayCart into one method.
    public static void promptItems(ArrayList<Product> sourcedList, ArrayList<Product> changedList, Scanner scanner, boolean add) {
        boolean isRunning = true;

        while (isRunning) {
            System.out.print("Enter the SKU (N to exit): ");
            String choice = scanner.nextLine();
            String operation = add ? "added" : "removed";

            switch (choice.toUpperCase()) {
                case "N":
                    System.out.println("\nReturning to main menu . . .\n");
                    isRunning = false;
                    break;
                default:
                    Product item = findProductById(choice, sourcedList);
                    if (!(item == null)) {
                        if (add) {
                            changedList.add(item);
                        } else {
                            changedList.remove(item);
                        }
                        System.out.println(item.getName() + " successfully "+ operation +"!");
                    } else {
                        System.out.println("Error, SKU " + choice + " not found. Returning to main menu.");
                    }
            }
        }
    }

    // This method should display the items in the cart ArrayList, along
    // with the total cost of all items in the cart. The method should
    // prompt the user to remove items from their cart by entering the ID
    // of the product they want to remove. The method should update the cart ArrayList and totalAmount
    // variable accordingly.
    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {
        StringBuilder sb = new StringBuilder();
        for (Product product : cart) {
            sb.append(product.toString()).append("\n");
        }
        if (sb.isEmpty()) {
            System.out.println("\nNothing was added to the cart!\n");
        } else {
            System.out.println("\n"+sb);

            int choice = -1;

            while (choice != 3) {
                System.out.println("1. Check Out");
                System.out.println("2. Remove Product");
                System.out.println("3. Return to main Menu");
                System.out.print("\nEnter: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                // Call the appropriate method based on user choice
                switch (choice) {
                    case 1:
                        checkOut(cart, totalAmount, scanner);
                        break;
                    case 2:
                        System.out.println("Do you want to remove items from your cart?");
                        promptItems(cart, cart, scanner, false);
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("\nInvalid choice!");
                        break;
                }
            }
        }
    }

    // This method should calculate the total cost of all items in the cart,
    // and display a summary of the purchase to the user. The method should
    // prompt the user to confirm the purchase, and deduct the total cost
    // from their account if they confirm.
    public static void checkOut(ArrayList<Product> cart, double totalAmount, Scanner scanner) {
        for (Product product : cart) {
            totalAmount += product.getPrice();
        }

        System.out.println("Your total is: $" + totalAmount);
        System.out.print("Do you want to purchase these items? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            System.out.println("Items were purchased successfully.");
            totalAmount = 0;
        } else if(scanner.nextLine().equalsIgnoreCase("N")) {
            System.out.println("Items were not purchased.");
        } else {
            System.out.println("Invalid input.");
        }
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