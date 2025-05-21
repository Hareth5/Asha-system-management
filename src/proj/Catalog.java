package proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Catalog { // a class contain the main project data structures
    private static DoubleLinkedlist categories;
    private static Cursor cursorArray[][];
    private static Cursor products;
    private static Cursor cancelledProducts;
    private static QueueOfLinkedList shipments = new QueueOfLinkedList();
    private static StackOfLinkedList undo;
    private static StackOfLinkedList redo;
    private static ObservableList<Category> categoryList;
    private static ObservableList<Product> productList;
    private static ObservableList<Record> shipmentsRecord;

    public Catalog() { // initialize the data structures
        categories = new DoubleLinkedlist();
        cursorArray = new Cursor[26][10];
        cancelledProducts = new Cursor();
        cancelledProducts.createList();
        products = new Cursor();

        categoryList = FXCollections.observableArrayList();
        productList = FXCollections.observableArrayList();
        shipmentsRecord = FXCollections.observableArrayList();

        undo = new StackOfLinkedList();
        redo = new StackOfLinkedList();

        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 10; j++) {
                cursorArray[i][j] = new Cursor();
                cursorArray[i][j].createList();
            }
        }
    }

    public static DoubleLinkedlist getCategories() {
        return categories;
    }

    public static Cursor getProducts() {
        return products;
    }

    public static Cursor getCancelledProducts() {
        return cancelledProducts;
    }

    public static Cursor[][] getCursorArray() {
        return cursorArray;
    }

    public static QueueOfLinkedList getShipments() {
        return shipments;
    }

    public static StackOfLinkedList getUndo() {
        return undo;
    }

    public static StackOfLinkedList getRedo() {
        return redo;
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
