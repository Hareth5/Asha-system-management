package proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static proj.Catalog.*;
import static proj.Tableview.*;

public class ProductManagement extends CategoryManagement { // class for product management
    private Button categoryManagement;
    private ComboBox<String> category, sortBy;
    private ToggleGroup toggleGroup;
    private RadioButton all, active, inactive;
    private Product product;

    public ProductManagement() {
        initialize();
        initializeComboBoxes();
        actions();
    }

    private void initialize() { // initialize the scene components
        categoryManagement = new Button("Category Management");

        toggleGroup = new ToggleGroup();
        all = new RadioButton("Show all products");
        active = new RadioButton("Show active products");
        inactive = new RadioButton("Show inactive products");
        all.setSelected(true);
    }

    private void initializeComboBoxes() { // initialize the scene combo boxes
        category = new ComboBox<>();
        category.setValue("Select all categories");

        sortBy = new ComboBox<>();
        sortBy.getItems().addAll("Sort by ID", "Sort by name", "Sort by category", "Sort by status");
        sortBy.setValue("Sort by ID");
    }

    private VBox radioButtons() { // initialize the scene radio buttons
        all.setToggleGroup(toggleGroup);
        active.setToggleGroup(toggleGroup);
        inactive.setToggleGroup(toggleGroup);

        VBox radioButtons = new VBox(5, all, active, inactive);
        radioButtons.setPadding(new Insets(0, 0, 0, 465));
        return radioButtons;
    }

    private HBox top() { // a method to assemble the top components together
        HBox filters = new HBox(20, category, sortBy, radioButtons());
        filters.setAlignment(Pos.CENTER_LEFT);
        filters.setPadding(new Insets(0, 0, 0, 20));
        return filters;
    }

    private TableView<Product> center2() { // a method to get the table view
        return getProductTable();
    }

    private VBox right() { // a method to assemble the right components together
        VBox btns = new VBox(20, search(), categoryManagement);
        btns.setPadding(new Insets(20));
        btns.setAlignment(Pos.TOP_CENTER);
        return btns;
    }


    public BorderPane main() { // a method to assemble all components together
        BorderPane main = new BorderPane();
        main.setTop(top());
        main.setCenter(center2());
        main.setRight(right());
        main.setBottom(bottom());
        BorderPane.setMargin(main.getCenter(), new Insets(20));
        return main;
    }

    private void actions() { // a method to handle all action
        categoryManagement.setOnAction(e -> Main.setMain(new CategoryManagement().main()));

        searching();
        update.setOnAction(e -> update());
        remove.setOnAction(e -> remove());

        category.setOnAction(e -> changeCategory());
        sortBy.setOnAction(e -> sorting());
        filtering();
    }

    private void searching() { // searching for an existing product and cancel search button
        search.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                String searchBy = search.getText().trim();
                if (searchBy.equals("") || (!searchBy.matches("^[0-9]+$") && !searchBy.matches("^[a-zA-Z ]+$"))) {
                    MyAlert.alert("Warning", "You should enter a valid ID or username to search", Alert.AlertType.WARNING);
                    return;
                }

                if (Character.isDigit(searchBy.charAt(0)))
                    searchIDName(searchBy, true);
                else
                    searchIDName(searchBy, false);
            }
        });

        cancel.setOnAction(e -> {
            search.setText("");
            getProductTable().setItems(getProductList());
            cancel.setDisable(true);
            remove.setDisable(true);
            update.setDisable(true);
        });
    }

    private void update() { // a method to update an existing product info
        if (product == null) {
            MyAlert.alert("Error", "You should search for product to update", Alert.AlertType.ERROR);
            cancel.setDisable(true);
            remove.setDisable(true);
            update.setDisable(true);
            return;
        }
        Main.setMain(new UpdateProduct(product).main());
    }

    private void remove() { // a method to update an existing product info
        if (product == null) {
            MyAlert.alert("Error", "You should search for product to update", Alert.AlertType.ERROR);
            cancel.setDisable(true);
            remove.setDisable(true);
            update.setDisable(true);
            return;
        }

    }

    private void changeCategory() {
        String cat = category.getValue();
        if (cat.equals("Select all categories"))
            getProductTable().setItems(getProductList());
        else {
        }
    }

    private void sorting() { // a method to sort products by name, category and status

    }

    private void filtering() { // a method to filter products by category and status
        all.setOnAction(e -> getProductTable().setItems(getProductList()));

        active.setOnAction(r -> {

        });

        inactive.setOnAction(e -> {

        });
    }

    private void searchIDName(String searchBy, boolean searchID) {  // searching for the product by name or ID
        ObservableList<Product> temp = FXCollections.observableArrayList();
        ObservableList<Product> list;

        if (searchID) {


        } else {

        }


        if (temp.isEmpty()) {
            MyAlert.alert("Not found", "There are no products starts with this ID or Name", Alert.AlertType.INFORMATION);
            return;
        }

        getProductTable().setItems(temp);
        product = temp.getFirst();
        cancel.setDisable(false);
        remove.setDisable(false);
        update.setDisable(false);
    }
}
