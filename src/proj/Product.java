package proj;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product implements Comparable<Product> { // a class for product
    private SimpleStringProperty id, name, status;
    private SimpleIntegerProperty amount;
    private Category category;
    public static int productCounter = 1;
    public static int productsNumber = 0;
    public static int activeProducts = 0;
    public static int inActiveProducts = 0;
    private int numOfShipments;

    public Product(SimpleStringProperty id, SimpleStringProperty name, SimpleStringProperty status, SimpleIntegerProperty amount, Category category) throws IllegalArgumentException {
        this.id = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
        this.amount = new SimpleIntegerProperty();

        setId(id.get());
        setName(name.get());
        setAmount(amount.get());
        setCategory(category);
        setStatus(status.get());
        productsNumber++;
        numOfShipments = 0;
    }

    // getters and setters for product data

    public String getId() {
        return id.get();
    }

    public void setId(String id) throws IllegalArgumentException {
        if (isNull(id))
            throw new IllegalArgumentException("ID cannot be null");

        if (!validation(id, "[0-9]+"))
            throw new IllegalArgumentException("ID must contain only numbers");

        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) throws IllegalArgumentException {
        if (isNull(name))
            throw new IllegalArgumentException("Name cannot be empty");

        if (!validation(name, "^[a-zA-Z ]+$"))
            throw new IllegalArgumentException("Name must contain only letters, numbers, and spaces");

        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        this.name.set(name);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) throws IllegalArgumentException {
        if (isNull(status))
            throw new IllegalArgumentException("Status cannot be empty");

        if (!status.equals("Active") && !status.equals("Inactive"))
            throw new IllegalArgumentException("Status must be 'Active' or 'Inactive'");

        if (status.equals("Active"))
            activeProducts++;
        else
            inActiveProducts++;

        this.status.set(status);
    }

    public int getAmount() {
        return amount.get();
    }

    public void setAmount(int amount) throws IllegalArgumentException {
        if (amount < 0)
            throw new IllegalArgumentException("Amount must be positive integer");

        this.amount.set(amount);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) throws IllegalArgumentException {
        if (category == null)
            throw new IllegalArgumentException("Category cannot be null");

        this.category = category;
    }

    public void setNumOfShipments(int numOfShipments) {
        this.numOfShipments = numOfShipments;
    }

    public int getNumOfShipments() {
        return numOfShipments;
    }

    private boolean isNull(String value) { // a method to check if the input is null
        return value == null || value.trim().isEmpty();
    }

    private boolean validation(String value, String regex) { // a method to check if the input has a valid expression
        return value.matches(regex);
    }

    @Override
    public int compareTo(Product product) { // comparable by name
        return this.getName().compareTo(product.getName());
    }

    @Override
    public String toString() { // toString method
        return getId() + ", " + getName();
    }
}