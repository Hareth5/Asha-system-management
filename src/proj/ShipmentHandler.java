package proj;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;

import static proj.Shipment.shipmentCounter;

public class ShipmentHandler { // class for adding a new shipment and updating an existing shipment
    public boolean handler(String data[], Product product, String date, boolean fromFile) {
        SimpleStringProperty id = new SimpleStringProperty(data[0]);
        SimpleIntegerProperty amount;

        try {
            amount = new SimpleIntegerProperty(Integer.parseInt(data[1]));
        } catch (NumberFormatException e) {
            MyAlert.alert("Error", "Amount must be a numerical value", Alert.AlertType.ERROR);
            return false;
        }

        try {
            Shipment newShipment = new Shipment(id, product, amount, date, fromFile);
            shipmentCounter++;
            newShipment.getProduct().setNumOfShipments(newShipment.getProduct().getNumOfShipments() + 1);
            Catalog.getShipments().enqueue(newShipment);

            Record record = new Record(newShipment, "Add");
            Catalog.getUndo().push(record);
            Catalog.getShipmentsRecord().add(record);

        } catch (IllegalArgumentException e) {
            MyAlert.alert("Error", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }
}
