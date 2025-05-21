package proj;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import static proj.Styling.*;

public class AddProduct { // class for adding a new product
    protected Label title, lid, lName, lUnitPrice, lDiscount, lCategory, lManufacturer;
    protected TextField id, name, unitPrice, discount;
    protected ComboBox<String> category, manufacturer;
    protected Button cancel, addCategory, action;
    private Product product;

    public AddProduct(Product product) {
        this.product = product;
        initializeLabels();
        initializeTxts();
        initializeButtons();
        actions();
    }

    private void initializeLabels() { // initialize the scene labels
        title = new Label("Add new Product");
        lid = new Label("Product ID:");
        lName = new Label("Product Name:");
        lUnitPrice = new Label("Unit price:");
        lDiscount = new Label("Discount:");
        lCategory = new Label("Category :");
        lManufacturer = new Label("Manufacturer :");

        setTitlesStyle(title);
    }

    private void initializeTxts() { // initialize the scene text fields
        id = new TextField();
        name = new TextField();
        unitPrice = new TextField();
        discount = new TextField();

        id.setText(String.valueOf(Product.productCounter));
        id.setEditable(false);
        setDisableTxtStyle(id);

        category = new ComboBox<>();
        manufacturer = new ComboBox<>();
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

        data.addColumn(0, lid, lName, lUnitPrice, lDiscount, lCategory, lManufacturer);
        data.addColumn(1, id, name, unitPrice, discount, category, manufacturer);
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
//            boolean action = new ProductHandler().handler(new String[]{id.getText().trim(), name.getText().trim(),
//                    unitPrice.getText().trim(), discount.getText().trim(), category.getValue().trim(),}, product);
//
//            if (action) {
//                name.setText("");
//                category.setValue(category.getItems().getFirst());
//            }
        });
    }
}
