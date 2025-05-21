package proj;

public class Record { // a class for saving shipments records
    private Shipment shipment;
    private String status;

    public Record(Shipment shipment, String status) {
        this.shipment = shipment;
        this.status = status;
    }

    // getters and setters

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
