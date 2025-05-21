package proj;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import static proj.Styling.*;

public class NewShipment { // class for adding a new shipment
    protected Label title, lid, lProductName, lManufacturer, lDate;
    protected TextField id, manufacturer;
    protected ComboBox<Product> chosenProduct;
    protected DatePicker date;
    protected Button cancel, action, addProduct;

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

        setTitlesStyle(title);
    }

    private void initializeTxts() { // initialize the scene text fields
        id = new TextField();
        manufacturer = new TextField();

        id.setEditable(false);
        id.setText(String.valueOf(Shipment.shipmentCounter));
        setDisableTxtStyle(id);

        date = new DatePicker();

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
    }

    protected GridPane left() { // a method to assemble the left components together
        GridPane data = new GridPane();
        data.setVgap(10);
        data.setHgap(10);
        data.setAlignment(Pos.TOP_LEFT);
        data.setPadding(new Insets(0, 20, 20, 20));

        data.addColumn(0, lid, lManufacturer, lDate, lProductName);
        data.addColumn(1, id, manufacturer, date, chosenProduct);
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
        action.setOnAction(e -> addShipment());
    }

    private void addShipment() { // // a method to add new shipment for an existing product
        String data[] = {id.getText(), manufacturer.getText()};
        Product product = chosenProduct.getValue();
        if (date.getValue() == null) {
            MyAlert.alert("Error", "Date cannot be null", Alert.AlertType.ERROR);
            return;
        }
        String localDate = date.getValue().toString();
//        boolean add = new ShipmentHandler().handler(data, product, localDate, false);
//        if (add) {
//            id.setText(String.valueOf(Shipment.shipmentCounter));
//            chosenProduct.setValue(chosenProduct.getItems().getFirst());
//            amount.setText("");
//            date.setValue(null);
//            MyAlert.alert("Success", "Shipment added successfully", Alert.AlertType.INFORMATION);
//        }
    }
}
