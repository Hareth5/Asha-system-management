package proj;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Shipment { // a class for shipments
    private SimpleStringProperty id;
    private SimpleIntegerProperty amount;
    private LocalDate date;
    private Product product;
    private boolean fromFile;
    public static int shipmentCounter = 1;

    public Shipment(SimpleStringProperty id, Product product, SimpleIntegerProperty amount, String date, boolean fromFile) throws IllegalArgumentException {
        this.id = new SimpleStringProperty();
        this.amount = new SimpleIntegerProperty();

        this.fromFile = fromFile;

        setId(id.get());
        setProduct(product);
        setAmount(amount.get());
        setDate(date);
    }

    // getters and setters for shipments data

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        if (isNull(id))
            throw new IllegalArgumentException("ID cannot be null");

        if (!validation(id, "[0-9]+"))
            throw new IllegalArgumentException("ID must contain only numbers");

        this.id.set(id);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) throws IllegalArgumentException {
        if (product == null)
            throw new IllegalArgumentException("Product cannot be null");

        this.product = product;
    }

    public int getAmount() {
        return amount.get();
    }

    public void setAmount(int amount) throws IllegalArgumentException {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be positive");

        this.amount.set(amount);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String date) throws IllegalArgumentException {
        if (isNull(date))
            throw new IllegalArgumentException("Date string cannot be null or empty");

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            LocalDate currentDate = LocalDate.now();

            if (fromFile) {
                if (localDate.isAfter(currentDate))
                    throw new IllegalArgumentException("Date cannot be in the future");

            } else {
                if (!localDate.isEqual(currentDate))
                    throw new IllegalArgumentException("Date must be today only");
            }

            this.date = localDate;

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }

    private boolean isNull(String value) { // a method to check if the input is null
        return value == null || value.trim().isEmpty();
    }

    private boolean validation(String value, String regex) { // a method to check if the input has a valid expression
        return value.matches(regex);
    }
}