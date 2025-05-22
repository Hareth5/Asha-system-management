package proj;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Shipment {
    private int shipmentID;
    private LocalDate date;
    private Manufacturer manufacturer;
    //    private Queue<Entry> entry = new LinkedList<>();
    public static int shipmentCounter = 1;

    public Shipment(int entryID, Manufacturer manufacturer, String date) {
        this.shipmentID = entryID;
        this.manufacturer = manufacturer;
        setDate(date);
    }

    public int getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(int shipmentID) {
        this.shipmentID = shipmentID;
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

//            if (!localDate.isEqual(currentDate))
//                throw new IllegalArgumentException("Date must be today only");

            this.date = localDate;

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    //    public Queue<Entry> getEntry() {
//        return entry;
//    }
//
//    public void setEntry(Queue<Entry> entry) {
//        this.entry = entry;
//    }
    private boolean isNull(String value) { // a method to check if the input is null
        return value == null || value.trim().isEmpty();
    }
}
