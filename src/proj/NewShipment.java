package proj;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static proj.Styling.*;

public class NewShipment { // class for adding a new shipment
    protected Label title, lid, lProductName, lManufacturer, lDate, lAmount, lStartDate, lEndDate;
    protected TextField id, manufacturer, amount;
    protected ComboBox<Product> chosenProduct;
    protected DatePicker date, startDate, endDate;
    protected Button cancel, action, addProduct, add;

    public NewShipment() {
        initializeLabels();
        initializeTxts();
        initializeComboBox();
        initializeButtons();
        actions();
    }

    private void initializeLabels() { // initialize the scene labels
        title = new Label("Add new Shipment");
        lid = new Label("Shipment ID:");
        lProductName = new Label("Product Name:");
        lManufacturer = new Label("Manufacturer:");
        lDate = new Label("Date:");
        lAmount = new Label("Amount:");
        lStartDate = new Label("Date:");
        lEndDate = new Label("Date:");

        setTitlesStyle(title);
    }

    private void initializeTxts() { // initialize the scene text fields
        id = new TextField();
        manufacturer = new TextField();
        amount = new TextField();

        id.setEditable(false);
        id.setText(String.valueOf(Shipment.shipmentCounter));
        setDisableTxtStyle(id);

        date = new DatePicker();
        startDate = new DatePicker();
        endDate = new DatePicker();

        setTxtStyle(id);
        setTxtStyle(manufacturer);
        setDatePickerStyle(date);
    }

    protected void initializeComboBox() { // initialize the scene combo boxes
        chosenProduct = new ComboBox<>();
        chosenProduct.getItems().addAll(Catalog.getProductList());

        if (!chosenProduct.getItems().isEmpty())
            chosenProduct.setValue(chosenProduct.getItems().getFirst());
    }

    private void initializeButtons() { // initialize the scene button
        cancel = new Button("Cancel");
        action = new Button("Confirm");
        addProduct = new Button("Add new product");
        add = new Button("Add");
    }

    protected GridPane left() { // a method to assemble the left components together
        GridPane data = new GridPane();
        data.setVgap(10);
        data.setHgap(10);
        data.setAlignment(Pos.TOP_LEFT);
        data.setPadding(new Insets(0, 20, 20, 20));

        data.addColumn(0, lid, lManufacturer, lDate, lProductName, lAmount, lStartDate, lEndDate, new Label());
        data.addColumn(1, id, manufacturer, date, chosenProduct, amount, startDate, endDate, add);
        return data;
    }

    protected HBox bottom() { // a method to assemble the bottom components together
        HBox btns = new HBox(100, cancel, addProduct, action);
        btns.setAlignment(Pos.CENTER);
        btns.setPadding(new Insets(20));
        return btns;
    }

    protected BorderPane main() { // a method to assemble all components together
        BorderPane main = new BorderPane();
        main.setTop(title);
        main.setLeft(left());
        main.setBottom(bottom());

        BorderPane.setAlignment(main.getTop(), Pos.CENTER);
        BorderPane.setMargin(main.getTop(), new Insets(20));
        return main;
    }

    private void actions() { // a method to handle all action
        cancel.setOnAction(e -> Main.setMain(new ProductManagement().main()));
        addProduct.setOnAction(e -> Main.setMain(new AddProduct(null).main()));
        add.setOnAction(e -> addEntry());
        action.setOnAction(e -> addShipment());
    }

    private void addEntry() {
        Product product = chosenProduct.getValue();
        int quantity = Integer.parseInt(amount.getText());
        LocalDate startDateValue = startDate.getValue();
        LocalDate endDateValue = endDate.getValue();
        Shipment shipment = new Shipment(Shipment.shipmentCounter, new Manufacturer(manufacturer.getText(), "bb"), String.valueOf(date));

        Entry entry = new Entry(product, shipment, quantity, String.valueOf(startDateValue), String.valueOf(endDateValue));
        Shipment.shipmentCounter++;

        addEntryToDatabase(product.getProductId(), shipment.getShipmentID(), quantity, startDateValue, endDateValue);
    }

    private void addShipment() { // a method to add new shipment for an existing product
        id.setText(String.valueOf(Shipment.shipmentCounter));
        amount.setText("");
        manufacturer.setText("");
        date.setValue(null);
        startDate.setValue(null);
        endDate.setValue(null);
        if (!chosenProduct.getItems().isEmpty())
            chosenProduct.setValue(chosenProduct.getItems().getFirst());
        MyAlert.alert("Success", "Shipment added successfully", Alert.AlertType.INFORMATION);
    }

    private void addEntryToDatabase(int productId, int shipmentId, int quantity, LocalDate startDate, LocalDate endDate) {
        String url = "jdbc:mysql://127.0.0.1:3305/university";
        String username = "root";
        String password = "1234";

        String sql = "insert into entry (product_id, shipment_id, quantity, start_date, end_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ps.setInt(2, shipmentId);
            ps.setInt(3, quantity);
            ps.setDate(4, java.sql.Date.valueOf(startDate));
            ps.setDate(5, java.sql.Date.valueOf(endDate));

            ps.executeUpdate();
        } catch (SQLException e) {
            MyAlert.alert("Error", "", Alert.AlertType.ERROR);
        }
    }
}
