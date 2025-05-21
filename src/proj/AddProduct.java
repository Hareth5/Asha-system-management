package proj;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import static proj.Styling.*;

public class AddProduct { // class for adding a new product
    protected Label title, lid, lName, lStatus, lCategory;
    protected TextField id, name;
    protected ComboBox<String> category, status;
    protected Button cancel, addCategory, action;
    private Product product;

    public AddProduct(Product product) {
        this.product = product;
        initializeLabels();
        initializeTxts();
        initializeComboBoxes();
        initializeButtons();
        actions();
    }

    private void initializeLabels() { // initialize the scene labels
        title = new Label("Add new Product");
        lid = new Label("Product ID:");
        lName = new Label("Product Name:");
        lStatus = new Label("Status:");
        lCategory = new Label("Category :");

        setTitlesStyle(title);
    }

    private void initializeTxts() { // initialize the scene text fields
        id = new TextField();
        name = new TextField();


        id.setEditable(false);
        setDisableTxtStyle(id);
        id.setText(String.valueOf(Product.productCounter));
    }

    protected void initializeComboBoxes() { // initialize the scene combo boxes
        category = Catalog.getCategories().getAllCategoriesString();
        category.getItems().removeFirst();
        if (category.getItems().size() != 0)
            category.setValue(category.getItems().getFirst());

        status = new ComboBox<>();
        status.getItems().addAll("Active", "Inactive");
        status.setValue("Active");
    }

    private void initializeButtons() { // initialize the scene button
        cancel = new Button("Cancel");
        action = new Button("Add");
        addCategory = new Button("Add new Category");
    }

    protected GridPane left() { // a method to assemble the left components together
        GridPane data = new GridPane();
        data.setVgap(10);
        data.setHgap(10);
        data.setAlignment(Pos.TOP_LEFT);
        data.setPadding(new Insets(0, 20, 20, 20));

        data.addColumn(0, lid, lName, lStatus, lCategory);
        data.addColumn(1, id, name, status, category);
        return data;
    }

    protected HBox bottom() { // a method to assemble the bottom components together
        HBox btns = new HBox(100, cancel, addCategory, action);
        btns.setAlignment(Pos.CENTER);
        btns.setPadding(new Insets(20));
        return btns;
    }

    protected BorderPane main() {// a method to assemble all components together
        BorderPane main = new BorderPane();
        main.setTop(title);
        main.setLeft(left());
        main.setBottom(bottom());

        BorderPane.setAlignment(main.getTop(), Pos.CENTER);
        BorderPane.setMargin(main.getTop(), new Insets(20));
        return main;
    }

    private void actions() { // a method to handle all action
        cancel.setOnAction(e -> Main.setMain(new NewShipment().main()));
        addCategory.setOnAction(e -> Main.setMain(new AddCategory(null).main()));

        action.setOnAction(e -> { // adding new product
            if (category.getValue() == null) {
                MyAlert.alert("Error", "You should create a new category for this product", Alert.AlertType.ERROR);
                return;
            }
            boolean action = new ProductHandler().handler(new String[]{id.getText().trim(), name.getText().trim(),
                    category.getValue().trim(), status.getValue().trim()}, product);

            if (action) {
                id.setText(String.valueOf(Product.productCounter));
                name.setText("");
                status.setValue("Active");
                category.setValue(category.getItems().getFirst());
            }
        });
    }
}
