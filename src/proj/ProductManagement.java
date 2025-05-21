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
    private Button categoryManagement, shipmentManagement;
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
        shipmentManagement = new Button("Shipment Management");

        toggleGroup = new ToggleGroup();
        all = new RadioButton("Show all products");
        active = new RadioButton("Show active products");
        inactive = new RadioButton("Show inactive products");
        all.setSelected(true);
    }

    private void initializeComboBoxes() { // initialize the scene combo boxes
        category = Catalog.getCategories().getAllCategoriesString();
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
        VBox btns = new VBox(20, search(), categoryManagement, shipmentManagement);
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
        shipmentManagement.setOnAction(e -> Main.setMain(new ShipmentManagement().main()));

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

        boolean delete = MyAlert.alert("Remove product", " Are you sure you want to remove this product?", Alert.AlertType.CONFIRMATION);
        if (delete) {

            int l = product.getCategory().cursorIndex;
            int id = ProductHandler.getID(product);
            int name = ProductHandler.getName(product);

            getProducts().remove(product, l);
            getCursorArray()[name][id].remove(product, 1);
            getProductList().remove(product);

            Product.productsNumber--;
            if (product.getStatus().equals("Active"))
                Product.activeProducts--;
            else
                Product.inActiveProducts--;
            Main.setMain(new ProductManagement().main());
        }
    }

    private void changeCategory() {
        String cat = category.getValue();
        if (cat.equals("Select all categories"))
            getProductTable().setItems(getProductList());
        else {
            Category current = getCategories().findCategory(cat);
            ObservableList<Product> temp = getProducts().getAllProducts(current.cursorIndex);
            getProductTable().setItems(temp);
        }
    }

    private void sorting() { // a method to sort products by name, category and status
        String sort = sortBy.getValue();
        if (sort.equals("Sort by ID"))
            getProductTable().setItems(getProductList());

        else if (sort.equals("Sort by name")) {
            ObservableList<Product> temp = FXCollections.observableArrayList();

            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 10; j++) {
                    temp.addAll(getCursorArray()[i][j].getAllProducts(1));
                }
            }
            getProductTable().setItems(temp);

        } else if (sort.equals("Sort by category")) {
            ObservableList<Product> temp = getCategories().getAllProducts();
            getProductTable().setItems(temp);

        } else {
            ObservableList<Product> temp = getCategories().getAllActiveProducts();
            temp.addAll(getCategories().getAllInactiveProducts());
            getProductTable().setItems(temp);
        }
    }

    private void filtering() { // a method to filter products by category and status
        all.setOnAction(e -> getProductTable().setItems(getProductList()));

        active.setOnAction(r -> {
            ObservableList<Product> temp = getCategories().getAllActiveProducts();
            getProductTable().setItems(temp);
        });

        inactive.setOnAction(e -> {
            ObservableList<Product> temp = getCategories().getAllInactiveProducts();
            getProductTable().setItems(temp);
        });
    }

    private void searchIDName(String searchBy, boolean searchID) {  // searching for the product by name or ID
        ObservableList<Product> temp = FXCollections.observableArrayList();
        ObservableList<Product> list;

        if (searchID) {
            int idIndex = Integer.parseInt(searchBy) % 10;
            for (int i = 0; i < 26; i++) {
                Product product = getCursorArray()[i][idIndex].searchID(1, searchBy);
                if (product != null) {
                    temp.add(product);
                    break;
                }
            }

        } else {
            char letter = Character.toUpperCase(searchBy.charAt(0));
            int nameIndex = letter - 65;
            for (int i = 0; i < 10; i++) {
                list = getCursorArray()[nameIndex][i].searchName(1, searchBy);
                if (list.size() > 0)
                    temp.addAll(list);
            }
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
