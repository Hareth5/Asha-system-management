package proj;


import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Entry { // a class for shipments
    private Product product;
    private Shipment shipment;
    private SimpleIntegerProperty amount;
    private LocalDate startDate, endDate;

    public Entry(Product product, Shipment shipment, int amount, String startDate, String endDate) throws IllegalArgumentException {
        this.amount = new SimpleIntegerProperty();
        setProduct(product);
        setShipment(shipment);
        setAmount(amount);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    // getters and setters for shipments data
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public int getAmount() {
        return amount.get();
    }

    public void setAmount(int amount) throws IllegalArgumentException {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be positive");

        this.amount.set(amount);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setStartDate(String date) throws IllegalArgumentException {
        if (isNull(date))
            throw new IllegalArgumentException("Date string cannot be null or empty");

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            LocalDate currentDate = LocalDate.now();

//            if (!localDate.isEqual(currentDate))
//                throw new IllegalArgumentException("Date must be today only");

            this.startDate = localDate;

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }

    public void setEndDate(String date) throws IllegalArgumentException {
        if (isNull(date))
            throw new IllegalArgumentException("Date string cannot be null or empty");

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            LocalDate currentDate = LocalDate.now();

//            if (!localDate.isEqual(currentDate))
//                throw new IllegalArgumentException("Date must be today only");

            this.endDate = localDate;

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