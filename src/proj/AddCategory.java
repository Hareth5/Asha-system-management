package proj;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import static proj.Styling.*;

public class AddCategory { // class for adding a new category
    protected Label title, lName, lDescription;
    protected TextField name;
    protected TextArea description;
    protected Button cancel, action;
    private Category category;

    public AddCategory(Category category) {
        this.category = category;
        initializeLabels();
        initializeTxts();
        initializeButtons();
        actions();
    }

    private void initializeLabels() { // initialize the scene labels
        title = new Label("Add new Category");
        lName = new Label("Category Name:");
        lDescription = new Label("Category Description:");

        setTitlesStyle(title);
    }

    private void initializeTxts() { // initialize the scene text fields
        name = new TextField();
        description = new TextArea();

        description.setPrefWidth(220);
        description.setPrefHeight(150);
    }

    private void initializeButtons() { // initialize the scene button
        cancel = new Button("Cancel");
        action = new Button("Add");
    }

    protected GridPane left() { // a method to assemble the left components together
        GridPane data = new GridPane();
        data.setVgap(10);
        data.setHgap(10);
        data.setAlignment(Pos.TOP_LEFT);
        data.setPadding(new Insets(0, 20, 20, 20));

        data.addColumn(0, lName, lDescription);
        data.addColumn(1, name, description);
        return data;
    }

    protected HBox bottom() { // a method to assemble the bottom components together
        HBox btns = new HBox(200, cancel, action);
        btns.setAlignment(Pos.CENTER);
        btns.setPadding(new Insets(20));
        return btns;
    }

    protected BorderPane main() { // a method to assemble all components together
        BorderPane main = new BorderPane();
        main.setTop(title);
        main.setLeft(left());
        main.setBottom(bottom());

        BorderPane.setAlignment(main.getTop(), Pos.CENTER);
        BorderPane.setMargin(main.getTop(), new Insets(20));
        return main;
    }

    private void actions() { // a method to handle all actions
        cancel.setOnAction(e -> Main.setMain(new AddProduct(null).main()));
        action.setOnAction(e -> { // adding new category
//            boolean action = new CategoryHandler().handler(new String[]{name.getText().trim(),
//                    description.getText().trim()}, false, category);
//
//            if (action) {
//                name.setText("");
//                description.setText("");
//            }
        });
    }
}
