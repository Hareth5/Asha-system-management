package proj;

public class Record { // a class for saving shipments records
    private Shipment shipment;
    private String status;
    public static int totalShipments = 0;
    public static int approvedShipments = 0;
    public static int canceledShipments = 0;
    public static Shipment maxQuantityShipment = null;

    public Record(Shipment shipment, String status) {
        this.shipment = shipment;
        this.status = status;
        statistics(shipment, status);
    }

    private void statistics(Shipment shipment, String status) { // a method to count approved shipments and canceled shipments
        totalShipments++;
        if (status.equals("Approved"))
            approvedShipments++;
        else if (status.equals("Canceled"))
            canceledShipments++;

        if (maxQuantityShipment == null)
            maxQuantityShipment = shipment;

        else if (status.equals("Approved") && shipment.getAmount() > maxQuantityShipment.getAmount())
            maxQuantityShipment = shipment;
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
