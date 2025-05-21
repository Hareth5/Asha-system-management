package proj;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import static proj.Catalog.*;
import static proj.Styling.setDisableTxtStyle;
import static proj.Styling.setUndoRedoButtonsStyle;

public class ShipmentManagement extends NewShipment { // class for shipment management
    private TextField productTxt;
    private Button undo, redo;

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
        amount.setEditable(false);
        date.setDisable(true);
        setDisableTxtStyle(id);
        setDisableTxtStyle(productTxt);
        setDisableTxtStyle(amount);

        undo = new Button();
        redo = new Button();
        undo.setGraphic(new ImageView("undo.png"));
        redo.setGraphic(new ImageView("redo.png"));
        setUndoRedoButtonsStyle(undo);
        setUndoRedoButtonsStyle(redo);
    }

    protected HBox undoRedo() { // undo and redo buttons
        HBox btns = new HBox(20, undo, redo);
        return btns;
    }

    @Override
    protected GridPane left() { // a method to assemble the left components together
        GridPane data = new GridPane();
        data.setVgap(10);
        data.setHgap(10);
        data.setAlignment(Pos.TOP_LEFT);
        data.setPadding(new Insets(0, 20, 20, 20));

        data.addRow(0, undoRedo(), new Label());
        data.addColumn(0, lid, lProductName, lQuantity, lDate);
        data.addColumn(1, id, productTxt, amount, date);
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
        if (getShipments().getSize() > 0) {
            Shipment shipment = (Shipment) getShipments().peek();
            id.setText(shipment.getId());
            productTxt.setText(shipment.getProduct().toString());
            amount.setText(String.valueOf(shipment.getAmount()));
            date.setValue(shipment.getDate());

        } else {
            id.setText("null");
            productTxt.setText("There are no products");
            amount.setText("null");
            date.setValue(null);

            cancel.setDisable(true);
            action.setDisable(true);
        }
    }

    private void actions() { // a method to handle all action
        cancel.setOnAction(e -> cancelShipment());
        action.setOnAction(e -> approveShipment());
        undo.setOnAction(e -> undo());
        redo.setOnAction(e -> redo());
    }

    private void cancelShipment() { // a method to cancel shipments
        if (MyAlert.alert("Conformation!", "Are you sure you want to cancel this shipment?",
                Alert.AlertType.CONFIRMATION)) {
            Shipment shipment = (Shipment) getShipments().dequeue();
            Record record = new Record(shipment, "Canceled");

            Catalog.getCancelledProducts().insertAtHead(shipment, 1);
            Catalog.getShipmentsRecord().addLast(record);
            Catalog.getUndo().push(record);

            shipment.getProduct().getCategory().setCanceledCategories(shipment.getProduct().getCategory().getCanceledCategories() + 1);
            Main.setMain(new ShipmentManagement().main());
        }
    }

    private void approveShipment() { // a method to approve shipments and store it in the inventory stock
        Shipment shipment = (Shipment) getShipments().dequeue();
        Product product = shipment.getProduct();
        Record record = new Record(shipment, "Approved");

        Catalog.getUndo().push(record);
        Catalog.getShipmentsRecord().addLast(record);

        product.setAmount(product.getAmount() + Integer.parseInt(amount.getText()));

        product.getCategory().setApprovedCategories(product.getCategory().getApprovedCategories() + 1);
        Main.setMain(new ShipmentManagement().main());
    }

    private void undo() { // undo button
        if (Catalog.getUndo().isEmpty() || ((Record)Catalog.getUndo().peek()).getStatus().equals("Add"))
            return;

        Record record = (Record) Catalog.getUndo().pop();
        Shipment shipment = record.getShipment();
        Product product = shipment.getProduct();

        if (record.getStatus().equals("Canceled")) {
            Shipment shipment1 = (Shipment) Catalog.getCancelledProducts().removeFirst(1);
            getShipments().enqueue(shipment1);

        } else {
            product.setAmount(product.getAmount() - shipment.getAmount());
            getShipments().enqueue(shipment);
        }

        int size = getShipments().getSize() - 1;
        while (size-- > 0) {
            getShipments().enqueue(getShipments().dequeue());
        }

        Catalog.getRedo().push(record);
        Catalog.getShipmentsRecord().removeLast();
        Main.setMain(new ShipmentManagement().main());
    }

    private void redo() { // undo button
        if (Catalog.getRedo().isEmpty())
            return;

        Record record = (Record) Catalog.getRedo().pop();
        Shipment shipment = record.getShipment();
        Product product = shipment.getProduct();

        if (record.getStatus().equals("Canceled")) {
            getShipments().dequeue();
            Catalog.getCancelledProducts().insertAtHead(shipment, 1);

        } else {
            product.setAmount(product.getAmount() + shipment.getAmount());
            getShipments().dequeue();
        }

        Catalog.getUndo().push(record);
        Catalog.getShipmentsRecord().addLast(record);
        Main.setMain(new ShipmentManagement().main());
    }
}
