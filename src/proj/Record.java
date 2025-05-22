package proj;

public class Record { // a class for saving shipments records
    private Entry shipment;
    private String status;

    public Record(Entry shipment, String status) {
        this.shipment = shipment;
        this.status = status;
    }

    // getters and setters

    public Entry getShipment() {
        return shipment;
    }

    public void setShipment(Entry shipment) {
        this.shipment = shipment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
