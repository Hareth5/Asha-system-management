package proj;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import static proj.Catalog.*;
import static proj.Styling.setDisableTxtStyle;

public class ShipmentManagement extends NewShipment { // class for shipment management
    private TextField productTxt;

    public ShipmentManagement() {
        super();
        initialize();
        fill();
        actions();
    }

    private void initialize() { // a method to initialize Main interface components
        title.setText("Shipments logs History");
        cancel.setText("Cancel shipment");
        action.setText("Approve Shipment");
        title.setPadding(new Insets(0, 0, 0, 400));

        productTxt = new TextField();
        id.setEditable(false);
        productTxt.setEditable(false);
        manufacturer.setEditable(false);
        date.setDisable(true);
        setDisableTxtStyle(id);
        setDisableTxtStyle(productTxt);
        setDisableTxtStyle(manufacturer);
    }


    @Override
    protected GridPane left() { // a method to assemble the left components together
        GridPane data = new GridPane();
        data.setVgap(10);
        data.setHgap(10);
        data.setAlignment(Pos.TOP_LEFT);
        data.setPadding(new Insets(0, 20, 20, 20));

        data.addColumn(0, lid, lProductName, lManufacturer, lDate);
        data.addColumn(1, id, productTxt, manufacturer, date);
        return data;
    }

    private TableView<Record> center() { // a method to assemble the center components together
        return Tableview.getShipmentTable();
    }

    @Override
    protected HBox bottom() { // a method to assemble the bottom components together
        HBox btns = new HBox(70, cancel, action);
        btns.setAlignment(Pos.CENTER);
        btns.setPadding(new Insets(20));
        return btns;
    }

    @Override
    protected BorderPane main() { // a method to assemble all components together
        BorderPane main = new BorderPane();
        main.setTop(title);
        main.setLeft(left());
        main.setCenter(center());
        main.setBottom(bottom());

        BorderPane.setAlignment(main.getTop(), Pos.CENTER);
        BorderPane.setMargin(main.getTop(), new Insets(20));
        BorderPane.setMargin(main.getCenter(), new Insets(0, 20, 20, 20));
        return main;
    }

    private void fill() { // a method to fill text fields automatically
        if (getShipments().size() > 0) {
            Entry shipment = (Entry) getShipments().peek();
            id.setText(String.valueOf(shipment.getProduct().getProductId()));
            productTxt.setText(shipment.getProduct().toString());
            manufacturer.setText(String.valueOf(shipment.getAmount()));
            date.setValue(shipment.getStartDate());

        } else {
            id.setText("null");
            productTxt.setText("There are no products");
            manufacturer.setText("null");
            date.setValue(null);

            cancel.setDisable(true);
            action.setDisable(true);
        }
    }

    private void actions() { // a method to handle all action
        action.setOnAction(e -> approveShipment());
    }

    private void approveShipment() { // a method to approve shipments and store it in the inventory stock
//        Shipment shipment = (Shipment) getShipments().dequeue();
//        Product product = shipment.getProduct();
//        Record record = new Record(shipment, "Approved");
//
//        Catalog.getUndo().push(record);
//        Catalog.getShipmentsRecord().addLast(record);
//
//        product.setTotalQuantity(product.getTotalQuantity() + Integer.parseInt(amount.getText()));
//
//        product.getCategory().setApprovedCategories(product.getCategory().getApprovedCategories() + 1);
//        Main.setMain(new ShipmentManagement().main());
    }
}
