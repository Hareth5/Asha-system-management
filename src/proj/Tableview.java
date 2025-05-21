package proj;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class Tableview { // a class for initializing all tableViews for showing data
    private static TableView<Category> categoriesTable = new TableView<>();
    private static TableView<Product> productsTable = new TableView<>();
    private static TableView<Record> shipmentsTable = new TableView<>();

    public static TableView<Category> getCategoriesTable() {
        return categoriesTable;
    }

    public static TableView<Product> getProductsTable() {
        return productsTable;
    }

    public static TableView<Record> getShipmentsTable() {
        return shipmentsTable;
    }

    public static TableView<Category> getCategoryTable() { // a method to initialize category table view
        TableColumn<Category, String> id = new TableColumn<>("ID");
        TableColumn<Category, String> name = new TableColumn<>("Name");
        TableColumn<Category, String> description = new TableColumn<>("Description");
        TableColumn<Category, String> amount = new TableColumn<>("Amount of products");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        amount.setCellValueFactory(cellData -> {
            Category category = cellData.getValue();
            int n = Catalog.getProducts().getSize(category.cursorIndex);
            return new SimpleStringProperty(String.valueOf(n));
        });

        categoriesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        categoriesTable.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #DDDDDD;");

        categoriesTable.getColumns().clear();
        categoriesTable.getColumns().addAll(id, name, description, amount);
        categoriesTable.setItems(Catalog.getCategoryList());
        return categoriesTable;
    }

    public static TableView<Product> getProductTable() { // a method to initialize product table view
        TableColumn<Product, String> id = new TableColumn<>("ID");
        TableColumn<Product, String> name = new TableColumn<>("Name");
        TableColumn<Product, String> status = new TableColumn<>("Status");
        TableColumn<Product, String> amount = new TableColumn<>("Amount");
        TableColumn<Product, Category> category = new TableColumn<>("Category");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));

        productsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        productsTable.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #DDDDDD;");

        productsTable.getColumns().clear();
        productsTable.getColumns().addAll(id, name, status, amount, category);
        productsTable.setItems(Catalog.getProductList());
        return productsTable;
    }

    public static TableView<Record> getShipmentTable() { // a method to initialize shipment table view
        TableColumn<Record, String> id = new TableColumn<>("Shipment ID");
        TableColumn<Record, String> product = new TableColumn<>("Product");
        TableColumn<Record, String> amount = new TableColumn<>("Amount");
        TableColumn<Record, String> date = new TableColumn<>("Date");
        TableColumn<Record, String> status = new TableColumn<>("status");

        id.setCellValueFactory(cellData -> {
            Record record = cellData.getValue();
            return new SimpleStringProperty(record.getShipment().getId());
        });

        product.setCellValueFactory(cellData -> {
            Record record = cellData.getValue();
            return new SimpleStringProperty(record.getShipment().getProduct().getName());
        });

        amount.setCellValueFactory(cellData -> {
            Record record = cellData.getValue();
            return new SimpleStringProperty(String.valueOf(record.getShipment().getAmount()));
        });

        date.setCellValueFactory(cellData -> {
            Record record = cellData.getValue();
            return new SimpleStringProperty(record.getShipment().getDate().toString());
        });

        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        shipmentsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        shipmentsTable.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #DDDDDD;");

        shipmentsTable.getColumns().clear();
        shipmentsTable.getColumns().addAll(id, product, amount, date, status);
        shipmentsTable.setItems(Catalog.getShipmentsRecord());
        return shipmentsTable;
    }
}
