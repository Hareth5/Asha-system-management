package proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;
import java.util.Queue;

public class Catalog { // a class contain the main project data structures
    private static Queue<Shipment> shipments = new LinkedList<>();
    private static ObservableList<Category> categoryList;
    private static ObservableList<Product> productList;
    private static ObservableList<Record> shipmentsRecord;

    public Catalog() { // initialize the data structures
        categoryList = FXCollections.observableArrayList();
        productList = FXCollections.observableArrayList();
        shipmentsRecord = FXCollections.observableArrayList();
    }

    public static Queue<Shipment> getShipments() {
        return shipments;
    }

    public static ObservableList<Category> getCategoryList() {
        return categoryList;
    }

    public static ObservableList<Product> getProductList() {
        return productList;
    }

    public static ObservableList<Record> getShipmentsRecord() {
        return shipmentsRecord;
    }
}
