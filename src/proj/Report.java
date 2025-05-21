//package proj;
//
//import javafx.scene.control.Label;
//import javafx.scene.control.TextArea;
//import javafx.scene.layout.BorderPane;
//
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//
//import static proj.Record.*;
//
//public class Report { // a class to show the full report about categories, product, and shipments
//    private TextArea report;
//
//    public Report() {
//        report = new TextArea();
//        report.setEditable(false);
//    }
//
//    private TextArea content() {
//        Label title = new Label("=========== WAREHOUSE REPORT ===========\n\n");
//        Styling.setTitlesStyle(title);
//        report.appendText(title.getText());
//
//        report.appendText("Total Products: " + Product.productsNumber + "\n");
//        report.appendText("Total Incoming Shipments: " + totalShipments + "\n");
//        report.appendText("Total Approved Shipments: " + approvedShipments + "\n");
//        report.appendText("Total Canceled Shipments: " + canceledShipments + "\n\n");
//
//        report.appendText("Shipment with Max Quantity: " + maxQuantity());
//
//        report.appendText("Most Recently Added Shipment: " + recentlyAddedShipment());
//
//        report.appendText("Cancel Rate Per Category: \n" + Catalog.getCategories().categoriesStatistics() + "\n\n");
//
//        report.appendText("Status Summary: \n" + "Active products: " + Product.activeProducts
//                + "\n Inactive products: " + Product.inActiveProducts + "\n\n");
//
//        report.appendText("Inventory Summary: \n" + inventorySummary() + "\n\n");
//
//        report.appendText("Number of shipments per product: \n" + countShipments());
//
//        return report;
//    }
//
//    private String maxQuantity() { // return the shipments with the max quantity
//        return (!Catalog.getUndo().isEmpty()) ? maxQuantityShipment.getId() + " (" +
//                maxQuantityShipment.getProduct().getName() + ") --> " + maxQuantityShipment.getAmount() +
//                " Units \n\n" : "There are no shipments\n\n";
//    }
//
//    private String recentlyAddedShipment() { // return the recently added shipment
//        if (Catalog.getUndo().isEmpty())
//            return "There are no shipments added\n\n";
//
//        Record record = recentlyAddedShipmentHelper();
//        Shipment shipment = record.getShipment();
//
//        if (!isBeforeMonth(shipment.getDate()))
//            return "There are no shipments added last month\n\n";
//
//        return shipment.getId() + " (" +
//                shipment.getProduct().getName() + ") --> " + shipment.getDate() + "\n\n";
//
//    }
//
//    private Record recentlyAddedShipmentHelper() {
//        if (((Record) Catalog.getUndo().peek()).getStatus().equals("Add"))
//            return (Record) Catalog.getUndo().peek();
//
//        Record temp = (Record) Catalog.getUndo().pop();
//        Record record = recentlyAddedShipmentHelper();
//        Catalog.getUndo().push(temp);
//        return record;
//    }
//
//    public boolean isBeforeMonth(LocalDate date) {
//        LocalDate curr = LocalDate.now();
//        LocalDate lastMonth = curr.minus(1, ChronoUnit.MONTHS);
//
//        return date.isBefore(lastMonth);
//    }
//
//    private String inventorySummary() { // return the products quantity
//        StringBuilder stb = new StringBuilder();
//        for (int i = 0; i < 26; i++) {
//            for (int j = 0; j < 10; j++) {
//                stb.append(Catalog.getCursorArray()[i][j].productSummary(1));
//            }
//        }
//        if (stb.isEmpty())
//            return "There are no products in the inventory stock";
//
//        return stb.toString();
//    }
//
//    private String countShipments() { // return the shipments quantity per product
//        StringBuilder stb = new StringBuilder();
//        for (int i = 0; i < 26; i++) {
//            for (int j = 0; j < 10; j++) {
//                stb.append(Catalog.getCursorArray()[i][j].countShipments(1));
//            }
//        }
//        if (stb.isEmpty())
//            return "There are no shipments";
//
//        return stb.toString();
//    }
//
//    public BorderPane main() {
//        BorderPane main = new BorderPane();
//        main.setCenter(content());
//        return main;
//    }
//}
